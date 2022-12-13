package client;

import core.Common;

import java.io.IOException;

public class Client1 {
    public static void main(String[] args) {
        boolean isGameActive = true;
        try (Common common = new Common("127.0.0.1", 8000)) {
            System.out.println("Connected to server");
            Object response = common.readObject(2);
            System.out.println("Response: " + response.toString());
            response = common.readObject(2);
            System.out.println("Response: " + response.toString());
            response = common.readObject(2);
            System.out.println("Response: " + response.toString());
            //загадывается слово
            response = common.readObject(3);
            System.out.println("Response: " + response.toString());

            System.out.println("Word");
            //что-то нарисовал
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