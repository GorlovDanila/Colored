import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static final int DEFAULT_PORT = 5555;

    public static void main(String[] args) {

        int port = DEFAULT_PORT;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        /* Создаем серверный сокет на полученном порту */
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Порт занят: " + port);
            System.exit(-1);
        }

        try {
            // ожидаем подключения клиентов
            Player players[] = new Player[2];

            for (int i = 0; i < 2; ++i) {
                players[i] = new Player();
                players[i].waitConnection(serverSocket);
            }

            System.out.println("Game started...\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}

