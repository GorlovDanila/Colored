package protocols;

import lombok.AllArgsConstructor;
import lombok.Data;
import protocols.handlers.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Data
public class MessagePacket {
    public static final byte HEADER_1 = (byte) 0xAF;
    public static final byte HEADER_2 = (byte) 0xAA;
    private static final byte HEADER_3 = (byte) 0xAF;
    public static final byte FOOTER_1 = (byte) 0xFF;
    public static final byte FOOTER_2 = (byte) 0x00;
    public static final byte TYPE_META = 1;
    public static final byte SUBTYPE_HANDSHAKE = 2;
    public static final byte SUBTYPE_GOODBYE = 3;
    public static final byte SUBTYPE_CORRECT_WORD = 7;
    public static final byte SUBTYPE_START_GAME = 9;
    public static final byte SUBTYPE_END_GAME = 10;
    public static final byte SUBTYPE_START_ROUND = 11;
    public static final byte SUBTYPE_END_ROUND = 12;
    public static final byte SUBTYPE_NEXT_ROUND = 13;
    public static final byte TYPE_BOARD = 4;
    public static final byte SUBTYPE_JSON = 5;
    public static final byte SUBTYPE_DEFAULT = 6;
    public static final byte TYPE_MESSAGE = 8;

    private byte packetType;
    private byte packetSubtype;
    private List<PacketField> fields = new ArrayList<>();

    private MessagePacket() {
    }

    public static MessagePacket create(byte type, byte subtype) {
        MessagePacket packet = new MessagePacket();
        packet.packetType = type;
        packet.packetSubtype = subtype;
        return packet;
    }

    public static boolean compareEOP(byte[] array, int last) {
        return array[last - 1] == FOOTER_1 && array[last] == FOOTER_2;
    }

    public static MessagePacket parse(byte[] data) {
        if (data[0] != HEADER_1 || data[1] != HEADER_2 || data[2] != HEADER_3
                || data[data.length - 1] != FOOTER_2 && data[data.length - 2] != FOOTER_1) {
            throw new IllegalArgumentException("Unknown packet");
        }
        var type = data[3];
        var subType = data[4];
        MessagePacket packet = MessagePacket.create(type, subType);
        switch (type) {
            case TYPE_META:
                switch (subType) {
                    case SUBTYPE_HANDSHAKE:
                        return HandshakeHandler.parse(data, packet);
                    case SUBTYPE_GOODBYE:
                        return GoodbyeHandler.parse(data, packet);
                    case SUBTYPE_CORRECT_WORD:
                        return WordHandler.parse(data, packet);
                    default:
                        return StartGameHandler.parse(data, packet);
                }
            case TYPE_BOARD:
                switch (subType) {
                    case SUBTYPE_JSON:
                        return JSONHandler.parse(data, packet);
                    case SUBTYPE_DEFAULT:
                        return DefaultHandler.parse(data, packet);
                }
                break;
            case TYPE_MESSAGE:
                switch (subType) {
                    case SUBTYPE_DEFAULT:
                        return DefaultHandler.parse(data, packet);
                }
                break;
        }
        return null;
    }

    public byte[] toByteArray() {
        try (ByteArrayOutputStream writer = new ByteArrayOutputStream()) {
            writer.write(new byte[]{HEADER_1, HEADER_2, HEADER_3});
            writer.write(packetType);
            writer.write(packetSubtype);
            switch (packetType) {
                case TYPE_META:
                    switch (packetSubtype) {
                        case SUBTYPE_HANDSHAKE:
                            HandshakeHandler.toByteArray(this, writer);
                            break;
                        case SUBTYPE_GOODBYE:
                            GoodbyeHandler.toByteArray(this, writer);
                            break;
                        case SUBTYPE_CORRECT_WORD:
                            WordHandler.toByteArray(this, writer);
                        default:
                            break;
                    }
                    break;
                case TYPE_BOARD:
                    switch (packetSubtype) {
                        case SUBTYPE_JSON:
                            JSONHandler.toByteArray(this, writer);
                            break;
                        case SUBTYPE_DEFAULT:
                            DefaultHandler.toByteArray(this, writer);
                            break;
                    }
                    break;
                case TYPE_MESSAGE:
                    switch (packetSubtype) {
                        case SUBTYPE_DEFAULT:
                            DefaultHandler.toByteArray(this,writer);
                    }
                    break;
            }

            writer.write(new byte[]{FOOTER_1, FOOTER_2});
            return writer.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PacketField getField(int id) {
        Optional<PacketField> field = getFields().stream().filter(f -> f.getFieldId() == (byte) id).findFirst();
        if (field.isEmpty()) {
            throw new IllegalArgumentException("No field with that id");
        }
        return field.get();
    }

    public Object getValue(int id) {
        PacketField field = getField(id);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(field.getContent());
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setValue(int id, Object value) {
        PacketField field;
        try {
            field = getField(id);
        } catch (IllegalArgumentException e) {
            field = new PacketField((byte) id);
        }
        if (packetType == TYPE_BOARD && packetSubtype == SUBTYPE_JSON) {
            getFields().add(JSONHandler.setValue(field, value));
        } else if (packetType == TYPE_BOARD && packetSubtype == SUBTYPE_DEFAULT) {
            getFields().add(DefaultHandler.setValue(field, value));
        } else if (packetType == TYPE_META && packetSubtype == SUBTYPE_CORRECT_WORD) {
            getFields().add(DefaultHandler.setValue(field, value));
        } else if (packetType == TYPE_META && packetSubtype == SUBTYPE_GOODBYE) {
            getFields().add(DefaultHandler.setValue(field, value));
        }
    }
}
