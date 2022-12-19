package protocols.handlers;

import protocols.MessagePacket;
import protocols.PacketField;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class HandshakeHandler {

    public static void toByteArray(MessagePacket packet, ByteArrayOutputStream bos) {
        String message = "";
        byte id = 0;
        if (packet.getFields().size() == 0) {
            message = "HAND";
            id = 1;
        } else if (packet.getFields().size() == 1) {
            message = "SHAKE";
            id = 2;
        }

        PacketField field = new PacketField(id);

        try (ByteArrayOutputStream output = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(output)) {
            oos.writeObject(message);
            byte[] data = output.toByteArray();
            if (data.length > 255) {
                throw new IllegalArgumentException("Too much data sent");
            }
            field.setFieldSize((byte) data.length);
            field.setContent(data);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        packet.getFields().add(field);
        try {
            for (PacketField f : packet.getFields()) {
                bos.write(new byte[]{f.getFieldId(), f.getFieldSize()});
                bos.write(f.getContent());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MessagePacket parse(byte[] data, MessagePacket packet) {
        int offset = 5;
        while (true) {
            if (data.length - 2 <= offset) {
                if (packet.getFields().size() == 2) {
                    if ((((String) packet.getValue(1)) + ((String) packet.getValue(2))).equals("HANDSHAKE")) {
                        System.out.println("Один протокол");
                    } else {
                        System.out.println("Разные протоколы");
                    }
                }
                return packet;
            }

            byte fieldId = data[offset];
            byte fieldSize = data[offset + 1];

            byte[] content = new byte[fieldSize];
            if (fieldSize != 0) {
                System.arraycopy(data, offset + 2, content, 0, fieldSize);
            }

            PacketField field = new PacketField(fieldId, fieldSize, content);
            packet.getFields().add(field);

            offset += 2 + fieldSize;
        }
    }
}
