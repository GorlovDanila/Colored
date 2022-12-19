package client;

import protocols.MessagePacket;

import java.io.*;

public class PlayerThread extends Thread {
    private final InputStream reader;
    private final OutputStream writer;
    private final PlayerClient client;

    public PlayerThread(InputStream reader, OutputStream writer, PlayerClient client) {
        this.reader = reader;
        this.writer = writer;
        this.client = client;
    }

    public InputStream getInput() {
        return reader;
    }

    public OutputStream getOutput() {
        return writer;
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

    private byte[] extendArray(byte[] oldArray) {
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

    private byte[] readInput(InputStream stream) throws IOException {
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
        //            while (isGameActive) {
        System.out.println("Connected to server");
        writeObject(client.getName(), 4, 5, 1);
        writeObject(client.getIdOfRoom(), 4, 5, 2);
        writeObject(client.getStage(),4, MessagePacket.SUBTYPE_JSON, 3);
        Object response = readObject(2);
        System.out.println("Response: " + response.toString());
        response = readObject(2);
        System.out.println("Response: " + response.toString());
        response = readObject(2);
        System.out.println("Response: " + response.toString());
        String role = (String) response;
        System.out.println(role);

        if (role.equals("Вы рисующий")) {
            System.out.println("DAFSgedgqeewrtq3t4yghjsr4wtrhytre3wthytr4tyhyr43tdhgre4");
            client.setRole("Drawer");
            //загадывается слово
            response = readObject(3);
            System.out.println("Response: " + response.toString());

            System.out.println("Word");
            //что-то нарисовал
            String request = "Visaginias1";
            System.out.println("Request: " + request);
            writeObject(request, 4, 5, 1);

            response = readObject(2);
            System.out.println("Response: " + response.toString());
        } else {
            client.setRole("Guesser");
            //ждём пока рисующий закончит

            // получаем доску
            response = readObject(2);
            System.out.println("Response: " + response.toString());

            //считываем ответ игрока
            String request = "Visaginias1";
            System.out.println("Request: " + request);
            writeObject(request, 4, 5, 1);

            response = readObject(2);
            System.out.println("Response: " + response.toString());
        }
    }
}
