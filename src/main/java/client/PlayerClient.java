package client;

import protocols.MessagePacket;

import java.io.*;
import java.net.Socket;

public class PlayerClient {

    private Socket clientSocket;
    private final String serverIp;
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
}
