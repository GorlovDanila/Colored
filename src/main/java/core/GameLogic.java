package core;

public class GameLogic {
    private String correctWord;

    private boolean isGameActive;

//    private Board board;
//
//    public Board getBoard() {
//        return board;
//    }
//
//    public void setBoard(Board board) {
//        this.board = board;
//    }

    public boolean isGameActive() {
        return isGameActive;
    }

    public void setGameActive(boolean gameActive) {
        isGameActive = gameActive;
    }

    public GameLogic() {
         isGameActive = true;
    }

    public void createBoard() {

    }
    public String getCorrectWord() {
        return correctWord;
    }

    public void setCorrectWord(String correctWord) {
        this.correctWord = correctWord;
    }

    public boolean equalsWords(String currentWord) {
        return currentWord.equals(correctWord);
    }
}
