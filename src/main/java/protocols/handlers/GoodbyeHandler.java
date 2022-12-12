package protocols.handlers;

import protocols.MessagePacket;
import protocols.PacketField;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static protocols.MessagePacket.FOOTER_1;
import static protocols.MessagePacket.FOOTER_2;

public class GoodbyeHandler {
    public static void toByteArray(MessagePacket packet, ByteArrayOutputStream writer) {
        writer.write(MessagePacket.SUBTYPE_GOODBYE);
        try {
            for (PacketField field: packet.getFields()) {
                writer.write(new byte[] {field.getFieldId(), field.getFieldSize()});
                writer.write(field.getContent());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MessagePacket parse(byte[] data) {

        MessagePacket packet = MessagePacket.create(MessagePacket.TYPE_META, MessagePacket.SUBTYPE_GOODBYE);
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
}
