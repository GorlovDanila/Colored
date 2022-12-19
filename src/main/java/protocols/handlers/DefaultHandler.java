package protocols.handlers;

import protocols.MessagePacket;
import protocols.PacketField;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static protocols.MessagePacket.FOOTER_1;
import static protocols.MessagePacket.FOOTER_2;

public class DefaultHandler {
    public static void toByteArray(MessagePacket packet, ByteArrayOutputStream writer) {
        try {
            for (PacketField field: packet.getFields()) {
                writer.write(new byte[] {field.getFieldId(), field.getFieldSize()});
                writer.write(field.getContent());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MessagePacket parse(byte[] data, MessagePacket packet) {
        int offset = 5;
        while (true) {
            if (data.length - 2 <= offset) {
                return packet;
            }

            byte fieldId = data[offset];
            byte fieldSize = data[offset + 1];

            if (fieldId == FOOTER_1 && fieldSize == FOOTER_2) {
                return packet;
            }

            byte[] content = new byte[fieldSize];
            if (fieldSize != 0) {
                System.arraycopy(data, offset + 2, content, 0, fieldSize);
            }

            PacketField field = new PacketField(fieldId, fieldSize, content);
            packet.getFields().add(field);

            offset += 2 + fieldSize;
        }
    }

    public static PacketField setValue(PacketField field, Object value) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(value);
            byte[] data = bos.toByteArray();
            if (data.length > 255) {
                throw new IllegalArgumentException("Too much data sent");
            }
            field.setFieldSize((byte) data.length);
            field.setContent(data);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return field;
    }
}
