package core;

import gui.controller.WaitingCreatorController;
import gui.controller.WaitingJoinerController;
import protocols.MessagePacket;

import java.util.List;

public class Room implements Runnable {
    private final int id;
    private List<Player> players;

    private int playersCount;

//    public static boolean isGameActive = false;

    public  List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Room(int id, List<Player> players, int playersCount) {
        this.id = id;
        this.players = players;
        this.playersCount = playersCount;
    }

    @Override
    public void run() {
//ЛЮДИ ОБЩАЮТСЯ ЧЕРЕЗ СЕРВАК

//        for (int i = 0; i < playersCount; i++) {
//            //players.get(i) = new Player(server);
//            players.add(new Player(server));
//            players.get(i).writeObject("Ждём других игроков", 4, 5, 2);
//        }
//        System.out.println(WaitingCreatorController.gameStartFlag);
//        if (WaitingCreatorController.gameStartFlag) {
        //ПЕРЕПИСАТЬ ВСЁ НА МЕТОДЫ
            if (playersCount == players.size()) {

//                System.out.println("Game started...\n");
//                for (Player player : players) {
//                    player.writeObject("Game started...", 4, 5, 2);
//                }


                startGame(players);
                sendPacket(players, MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_GAME);


                GameLogic logic = new GameLogic();

//                logic.setCorrectWord(currentWord());
                setCorrectWord(logic);

//                int idOfDrawer = setRoles(players, 0);
                setRoles(players);

//                for (Player player : players) {
//                    if (player.getRole().equals("Drawer")) {
//                        player.writeObject("Вы рисующий", MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_DEFAULT, 2);
//                        player.writeObject(logic.getCorrectWord(), 4, 5, 3);
//                    } else {
//                        player.writeObject("Вы угадывающий", 4, 5, 2);
//                    }
//                }
                notificationPlayersAboutRoles(players, logic);
                getPacket(players);

                String board = getBoard(players);
//                for (Player player : players) {
//                    if (player.getRole().equals("Drawer")) {
//                        board = (String) player.readObject(1);
//                    }
//                }

//                for (Player player : players) {
//                    if (!player.getRole().equals("Drawer")) {
//                        player.writeObject(board, 4, 5, 2);
//                    }
//                }

//                notificationPlayersAboutBoard(players, board);
//                notificationPlayerAboutGameResult(players, logic);

//                int countOfGuessed = 0;
                //читаем ответ игрока
//                for (Player player : players) {
//                    if (!player.getRole().equals("Drawer")) {
//                        String word = (String) player.readObject(1);
//                        if (logic.equalsWords(word)) {
////                            countOfGuessed++;
//                            player.writeObject("Вы угадали", 4, 5, 2);
//                        } else {
//                            player.writeObject("Вы не угадали", 4, 5, 2);
//                        }
//                    }
//                }
//
//                players.get(idOfDrawer).writeObject("Угадало " + countOfGuessed + " игроков", 4, 5, 2);
            }
        }
//    }

//    private static void setRoles(List<Player> players, int id) {
//
//        int idOfDrawer = id;
//        if(idOfDrawer == 0) {
//            players.get(idOfDrawer).setRole("Drawer");
//        } else {
//            players.get(idOfDrawer).setRole("Guesser");
//        }
//        if (idOfDrawer + 1 < players.size()) {
//            players.get(idOfDrawer + 1).setRole("Drawer");
//            idOfDrawer = idOfDrawer + 1;
//        } else {
//            players.get(0).setRole("Drawer");
//            idOfDrawer = 0;
//        }
//
//        for (Player player : players) {
//            if (!player.getRole().equals("Drawer")) {
//                player.setRole("Guesser");
//            }
//        }

//        return idOfDrawer;
//    }

    private static void setRoles(List<Player> players) {
        int idOfDrawer = -1;
        for (int i = 0; i < players.size(); i++) {
            if(players.get(i).getRole().equals("Drawer")) {
                idOfDrawer = i;
                break;
            }
        }
        if(idOfDrawer == -1) {
            players.get(0).setRole("Drawer");
        } else {
            players.get(idOfDrawer).setRole("Guesser");
        }
        if (idOfDrawer + 1 < players.size()) {
            players.get(idOfDrawer + 1).setRole("Drawer");
//            idOfDrawer = idOfDrawer + 1;
        } else {
            players.get(0).setRole("Drawer");
//            idOfDrawer = 0;
        }
    }

    private static void notificationPlayersAboutRoles(List<Player> players, GameLogic gameLogic) {
        for (Player player : players) {
            if (player.getRole().equals("Drawer")) {
                player.writeObject("Вы рисующий", MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_DEFAULT, 2);
                player.writeObject(gameLogic.getCorrectWord(), 4, 5, 3);
            } else {
                player.writeObject("Вы угадывающий", 4, 5, 2);
            }
        }
    }

    private static void notificationPlayersAboutBoard(List<Player> players, String board) {
        for (Player player : players) {
            if (!player.getRole().equals("Drawer")) {
                player.writeObject(board, 4, 5, 2);
            }
        }
    }

    private static void notificationPlayerAboutGameResult(List<Player> players, GameLogic gameLogic) {
        String nickGuessed = null;
        String idOfDrawer = null;
        while (nickGuessed == null) {
            for (Player player : players) {
                if (!player.getRole().equals("Drawer")) {
                    String word = (String) player.readObject(1);
                    if (gameLogic.equalsWords(word)) {
                        player.writeObject("Вы угадали", 4, 5, 2);
                        nickGuessed = player.getName();
                        break;
                    } else {
                        player.writeObject("Вы не угадали", 4, 5, 2);
                    }
                }
            }
        }

        for (Player player : players) {
            if(player.getRole().equals("Drawer")) {
                player.writeObject(nickGuessed + " угадал", 4, 5, 2);
            }
        }

//        players.get(idOfDrawer).writeObject("Угадало " + countOfGuessed + " игроков", 4, 5, 2);
    }

    private static String currentWord() {
        WordsRepository wordsRepository = new WordsRepository();
        List<String> words = wordsRepository.getWords();
        int max = words.size() - 1;
        int position = (int) (Math.random() * ++max);
        return words.get(position);
    }

    private static void startGame(List<Player> players) {
        System.out.println("Game started...\n");
        for (Player player : players) {
            player.writeObject("Game started...", 4, 5, 2);
        }
    }

    private String getBoard(List<Player> players) {
        String board = null;
        for (Player player : players) {
            if (player.getRole().equals("Drawer")) {
                board = (String) player.readObject(1);
            }
        }
        return board;
    }

    private static void setCorrectWord(GameLogic gameLogic) {
        gameLogic.setCorrectWord(currentWord());
    }

    private static void sendPacket(List<Player> players, int type, int subtype) {
        for (Player player : players) {
            player.writeMessage(type, subtype);
        }
    }

    private static void getPacket(List<Player> players) {
        boolean startRoundFlag = true;
        for (Player player : players) {
            if(!player.readPacket().equals("SUBTYPE_START_ROUND")) {
                startRoundFlag = false;
            }
        }
        if(startRoundFlag) {
//            WaitingCreatorController.latch.countDown();
//            WaitingJoinerController.latch.countDown();
              WaitingCreatorController.pauseFlag = false;
//              WaitingJoinerController.pauseFlag = false;
            for (Player player : players) {
                player.writeMessage(MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_ROUND);
//                WaitingCreatorController.latch.countDown();
//                WaitingJoinerController.latch.countDown();
            }
        }
    }
}
