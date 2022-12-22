package client;

import protocols.MessagePacket;

import java.io.*;

public class PlayerThread implements Runnable {
    private final InputStream reader;
    private final OutputStream writer;
    private final PlayerClient client;

//    public boolean isGetBoardFlag() {
//        return getBoardFlag;
//    }

//    public void setGetBoardFlag(boolean getBoardFlag) {
//       this.getBoardFlag = getBoardFlag;
//    }

//    public boolean getBoardFlag = false;

    public PlayerThread(InputStream reader, OutputStream writer, PlayerClient client) {
        this.reader = reader;
        this.writer = writer;
        this.client = client;
    }

//    public InputStream getInput() {
//        return reader;
//    }

//    public OutputStream getOutput() {
//        return writer;
//    }

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

//                response = readObject(2);
//                System.out.println("Response: " + response.toString());
            } else {
                client.setRole("Guesser");
                writeMessage(MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_ROUND);
                //ждём пока рисующий закончит

                // получаем доску
//                while (!getBoardFlag) {
//                    try {
//                        System.out.println(1);
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }

//                while (true) {
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    response = readObject(2);
//                    System.out.println("Response: " + response.toString());
//                }
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                response = readObject(2);
//                System.out.println("Response: " + response.toString());
               // writeObject(response, MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_DEFAULT, 4);
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                response = readObject(2);
//                System.out.println("Response: " + response.toString());
//
//                //считываем ответ игрока
//                String request = "Visaginias1";
//                System.out.println("Request: " + request);
//                writeObject(request, 4, 5, 1);
//
//                response = readObject(2);
//                System.out.println("Response: " + response.toString());
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
