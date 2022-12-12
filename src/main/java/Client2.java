import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client2 {
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 5555;

    public static void main(String[] args) {
        //Определяем хост сервера и порт
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            host = args[0];
        }
        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }

        try {
            //Создаем сокет для полученной пары хост/порт
            Socket socket = new Socket(host, port);

            System.out.println("connected.\n");

            // получаем потоки для чтения и записи в сокет
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();

            // создаем окно с кнопками


            // цикл обработки событий сервера
//            while (true) {
//                String fromServer = Common.readBytes(in);
//
//                // пробуем интерпретировать событие как Messages.Board
//            }
        } catch (UnknownHostException e) {
            System.out.println("Неизвестный хост: " + host);
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
