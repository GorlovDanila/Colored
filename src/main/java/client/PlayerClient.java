package client;

import protocols.MessagePacket;

import java.io.*;
import java.net.Socket;

public class PlayerClient {

    private Socket clientSocket;
    private String serverIp;
    private InputStream reader;
    private OutputStream writer;

    private PlayerThread gameThread;
    private String role;
    private String name;

    private boolean lobbyCreatorFlag = false;

    private int idOfRoom;

    private int countPlayers;

    public boolean isLobbyCreatorFlag() {
        return lobbyCreatorFlag;
    }

    public int getCountPlayers() {
        return countPlayers;
    }

    public void setCountPlayers(int countPlayers) {
        this.countPlayers = countPlayers;
    }
//    public boolean isLobbyCreatorFlag() {
//        return lobbyCreatorFlag;
//    }

    public void setLobbyCreatorFlag(boolean lobbyCreatorFlag) {
        this.lobbyCreatorFlag = lobbyCreatorFlag;
    }

    public int getIdOfRoom() {
        return idOfRoom;
    }

    public void setIdOfRoom(int idOfRoom) {
        this.idOfRoom = idOfRoom;
    }

    public PlayerClient(String serverIp) {
        this.serverIp = serverIp;
    }

    public PlayerThread getGameThread() {
        return gameThread;
    }

//    public void setGameThread(PlayerThread gameThread) {
//        this.gameThread = gameThread;
//    }

    public void start() throws IOException {
        clientSocket = new Socket(serverIp, 8000);
        this.reader = createReader();
        this.writer = createWriter();

        gameThread = new PlayerThread(reader, writer, this);
        new Thread(gameThread).start();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private InputStream createReader() throws IOException {
        return clientSocket.getInputStream();
    }

    private OutputStream createWriter() throws IOException {
        return clientSocket.getOutputStream();
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

//    public void writeMessage(int type, int subtype) {
//        try {
//            MessagePacket packet = MessagePacket.create((byte) type, (byte) subtype);
//            writer.write(packet.toByteArray());
//            writer.flush();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public String readPacket() {
//        try {
//            String result = "";
//            MessagePacket packet = MessagePacket.parse(readInput(reader));
//            assert packet != null;
//            switch (packet.getPacketSubtype()) {
//                case MessagePacket.SUBTYPE_START_GAME -> result = "SUBTYPE_START_GAME";
//                case MessagePacket.SUBTYPE_END_GAME -> result = "SUBTYPE_END_GAME";
//                case MessagePacket.SUBTYPE_START_ROUND -> result = "SUBTYPE_START_ROUND";
//                case MessagePacket.SUBTYPE_END_ROUND -> result = "SUBTYPE_END_ROUND";
//                case MessagePacket.SUBTYPE_NEXT_ROUND -> result = "SUBTYPE_NEXT_ROUND";
//                case MessagePacket.SUBTYPE_CORRECT_WORD -> result = "SUBTYPE_CORRECT_WORD";
//                case MessagePacket.SUBTYPE_JSON -> result = "SUBTYPE_JSON";
//            }
//            return result;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    private byte[] extendArray(byte[] oldArray) {
//        int oldSize = oldArray.length;
//        byte[] newArray = new byte[oldSize * 2];
//        System.arraycopy(oldArray, 0, newArray, 0, oldSize);
//        return newArray;
//    }

//    private byte[] readInput(InputStream stream) throws IOException {
//        int b;
//        byte[] buffer = new byte[10];
//        int counter = 0;
//        while ((b = stream.read()) > -1) {
//            buffer[counter++] = (byte) b;
//            if (counter >= buffer.length) {
//                buffer = extendArray(buffer);
//            }
//            if (counter > 1 && MessagePacket.compareEOP(buffer, counter - 1)) {
//                break;
//            }
//        }
//        byte[] data = new byte[counter];
//        System.arraycopy(buffer, 0, data, 0, counter);
//        return data;
//    }

//    public Object readObject(int id) {
//        try {
//            MessagePacket packet = MessagePacket.parse(readInput(reader));
//            assert packet != null;
//            return packet.getValue(id);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public void close() throws IOException {
//        writer.close();
//        reader.close();
//        clientSocket.close();
//    }
}
