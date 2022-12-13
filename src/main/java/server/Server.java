package server;

//import core.Common;

import core.GameLogic;
import core.Player;
import core.WordsRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.Random;

public class Server {
    public static void main(String[] args) throws IOException {

        int countClients = 2;

        try (ServerSocket server = new ServerSocket(8000)) {
            System.out.println("Server started");

            Player players[] = new Player[2];

            for (int i = 0; i < 2; i++) {
                players[i] = new Player(server);
                players[i].writeObject("Ждём других игроков", 4, 5, 2);
//                players[i].waitConnection(server);
            }

            System.out.println("Game started...\n");
            for (int i = 0; i < 2; i++) {
                players[i].writeObject("Game started...", 4, 5, 2);
            }

            GameLogic logic = new GameLogic();
            logic.setCorrectWord(currentWord());
//            while (logic.isGameActive()) {
                generateRandomTypes(players);
                if (players[0].getRole().equals("Drawer")) {
                    players[0].writeObject("Вы рисующий", 4, 5, 2);
                    players[0].writeObject(logic.getCorrectWord(), 4, 5, 3);
                    players[1].writeObject("Вы угадывающий", 4, 5, 2);
                } else {
                    players[0].writeObject("Вы угадывающий", 4, 5, 2);
                    players[1].writeObject("Вы рисующий", 4, 5, 2);
                }
                for (int i = 0; i < 1; i++) {
                    if(players[i].getRole().equals("Drawer")) {
                        // get board
                        players[i].readObject(1);
                        players[i+1].writeObject("board", 4, 5, 2);
                    }
                }

//                for (int i = 0; i < 1; i++) {
//                    if(!players[i].getRole().equals("Drawer")) {
                        //читаем ответ игрока
                        String word = (String) players[1].readObject(1);
                        System.out.println(word);
                        //сравниваем с загаданным словом
                        if(logic.equalsWords(word)) {
                            players[1].writeObject("Вы угадали", 4, 5, 2);
                            players[0].writeObject("Угадал 1 игрок", 4, 5, 2);
                        } else {
                            players[1].writeObject("Вы не угадали", 4, 5, 2);
                            players[0].writeObject("Угадало 0 игроков", 4, 5, 2);
                        }

//                        players[1].writeObject("Вы угадали", 4, 5, 2);
//                        players[0].writeObject("Угадало n игроков", 4, 5, 2);
//                    }
//                }
//            }

//            while (true) {
//                Common common = new Common(server);
//                new Thread(() -> {
//                    Object request = common.readObject(1);
//                    System.out.println("Request: " + request.toString());
//                    String response = (int) (Math.random() * 30 - 10) + "";
//                    try {
//                        Thread.sleep(4000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    common.writeObject(response, 4, 5, 2);
//                    System.out.println("Response: " + response);
//                    try { common.close();} catch (IOException e) {}
//                }).start();
//                server.accept();
//            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private static void generateRandomTypes(Player[] players) {
        Random rnd = new Random();
        int value = rnd.nextInt(1);

        if (value == 0) {
            players[0].setRole("Drawer");
            players[1].setRole("Guesser");
        } else {
            players[0].setRole("Guesser");
            players[1].setRole("Drawer");
        }
    }

    private static String currentWord() {
        WordsRepository wordsRepository = new WordsRepository();
        List<String> words = wordsRepository.getWords();
        int max = words.size()-1;
        int position = (int) (Math.random() * ++max);
        return words.get(position);
    }
}

