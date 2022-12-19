package core;

import javafx.stage.Stage;
import protocols.MessagePacket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Player {
    private final InputStream reader;
    private final OutputStream writer;
    private Socket clientSocket;
    private String role;
    private String name;

    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private String idOfRoom;

    public String getIdOfRoom() {
        return idOfRoom;
    }

    public void setIdOfRoom(String idOfRoom) {
        this.idOfRoom = idOfRoom;
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

    @Override
    public String toString() {
        return "Player{" +
                "reader=" + reader +
                ", writer=" + writer +
                ", clientSocket=" + clientSocket +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", idOfRoom='" + idOfRoom + '\'' +
                '}';
    }

    public Player(ServerSocket serverSocket) throws IOException {
        clientSocket = serverSocket.accept();
        System.out.print("Connection accepted.\n");
        this.reader = createReader();
        this.writer = createWriter();
        role = "";
        name = "";
        idOfRoom = "";
        stage = null;
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

    private byte[] extendArray(byte[] oldArray) {
        int oldSize = oldArray.length;
        byte[] newArray = new byte[oldSize * 2];
        System.arraycopy(oldArray, 0, newArray, 0, oldSize);
        return newArray;
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

    public Object readObject(int id) {
        try {
            MessagePacket packet = MessagePacket.parse(readInput(reader));
            assert packet != null;
            return packet.getValue(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() throws IOException {
        writer.close();
        reader.close();
        clientSocket.close();
    }
}
