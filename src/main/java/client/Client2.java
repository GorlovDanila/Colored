package client;

import core.Common;

import java.io.IOException;

public class Client2 {
    public static void main(String[] args) {
        try (Common common = new Common("127.0.0.1", 8000)) {
            System.out.println("Connected to server");
//            String request = "Visaginias1";
//            System.out.println("Request: " + request);
//            common.writeObject(request, 4, 5, 1);
           // Object response = common.readObject(2);
            //System.out.println("Response: " + response.toString());
            Object response = common.readObject(2);
            System.out.println("Response: " + response.toString());
            response = common.readObject(2);
            System.out.println("Response: " + response.toString());
            response = common.readObject(2);
            System.out.println("Response: " + response.toString());
            //ждём пока рисующий закончит

            // получаем доску
            response = common.readObject(2);
            System.out.println("Response: " + response.toString());

            //считываем ответ игрока
            String request = "Visaginias1";
            System.out.println("Request: " + request);
            common.writeObject(request, 4, 5, 1);

            response = common.readObject(2);
            System.out.println("Response: " + response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}