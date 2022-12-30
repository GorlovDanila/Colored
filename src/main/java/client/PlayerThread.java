package client;

import protocols.MessagePacket;

import java.io.*;

public class PlayerThread implements Runnable {
    private final InputStream reader;
    private final OutputStream writer;
    private final PlayerClient client;

    public PlayerThread(InputStream reader, OutputStream writer, PlayerClient client) {
        this.reader = reader;
        this.writer = writer;
        this.client = client;
    }

    public Object readObject(int id) {
        try {
            MessagePacket packet = MessagePacket.parse(readInput(reader));
            assert packet != null;
            return packet.getValue(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] extendArray(byte[] oldArray) {
        int oldSize = oldArray.length;
        byte[] newArray = new byte[oldSize * 2];
        System.arraycopy(oldArray, 0, newArray, 0, oldSize);
        return newArray;
    }

    public void writeObject(Object message, int type, int subtype, int id) {
        try {
            MessagePacket packet = MessagePacket.create((byte) type, (byte) subtype);
            packet.setValue(id, message);
            writer.write(packet.toByteArray());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] readInput(InputStream stream) throws IOException {
        int b;
        byte[] buffer = new byte[10];
        int counter = 0;
        while ((b = stream.read()) > -1) {
            buffer[counter++] = (byte) b;
            if (counter >= buffer.length) {
                buffer = extendArray(buffer);
            }
            if (counter > 1 && MessagePacket.compareEOP(buffer, counter - 1)) {
                break;
            }
        }
        byte[] data = new byte[counter];
        System.arraycopy(buffer, 0, data, 0, counter);
        return data;
    }


    @Override
    public void run() {
        //ПЕРЕПИСАТЬ ВСЁ НА МЕТОДЫ
        //            while (isGameActive) {
        System.out.println("Connected to server");

        Object response = readObject(2);
        System.out.println(response);
        response = readObject(2);
        System.out.println(response);
        response = readPacket();

        if (response.equals("SUBTYPE_START_GAME")) {

            response = readObject(2);
            System.out.println("Response: " + response.toString());
            String role = (String) response;
            System.out.println(role);

            if (role.equals("Вы рисующий")) {
                client.setRole("Drawer");
                writeMessage(MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_ROUND);
            } else {
                client.setRole("Guesser");
                writeMessage(MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_ROUND);
            }
        }
    }


    public String readPacket() {
        try {
            String result = "";
            MessagePacket packet = MessagePacket.parse(readInput(reader));
            assert packet != null;
            switch (packet.getPacketSubtype()) {
                case MessagePacket.SUBTYPE_START_GAME -> result = "SUBTYPE_START_GAME";
                case MessagePacket.SUBTYPE_END_GAME -> result = "SUBTYPE_END_GAME";
                case MessagePacket.SUBTYPE_START_ROUND -> result = "SUBTYPE_START_ROUND";
                case MessagePacket.SUBTYPE_END_ROUND -> result = "SUBTYPE_END_ROUND";
                case MessagePacket.SUBTYPE_NEXT_ROUND -> result = "SUBTYPE_NEXT_ROUND";
                case MessagePacket.SUBTYPE_CORRECT_WORD -> result = "SUBTYPE_CORRECT_WORD";
                case MessagePacket.SUBTYPE_JSON -> result = "SUBTYPE_JSON";
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeMessage(int type, int subtype) {
        try {
            MessagePacket packet = MessagePacket.create((byte) type, (byte) subtype);
            writer.write(packet.toByteArray());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
