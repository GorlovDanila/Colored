import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Player {

    private OutputStream m_out;
    private InputStream m_in;
    private Socket m_clientSocket;
    private char m_playerType;
    Player() {
        m_clientSocket = null;
        m_in = null;
        m_out = null;
        m_playerType = '_';
    }

    void waitConnection(ServerSocket serverSocket) throws IOException {
        m_clientSocket = serverSocket.accept();
        System.out.print("Connection accepted.\n");

        m_in = m_clientSocket.getInputStream();
        m_out = m_clientSocket.getOutputStream();
    }

//    void send(GameBoard board) {
//        Gson gson = new Gson();
//
//        Messages.Board boardMessage = new Messages.Board();
//        boardMessage.gameboard = board.toString();
//
//        boardMessage.your_type = m_playerType;
//        boardMessage.move = board.currentMove();
//        boardMessage.winner = board.getWinner();
//
//        Common.writeBytes(m_out, gson.toJson(boardMessage));
//    }
}
