package core;

public class GameLogic {
    private String correctWord;

    private boolean isGameActive;

    private boolean isRoundActive;

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

    public boolean isRoundActive() {
        return isRoundActive;
    }

    public void setRoundActive(boolean roundActive) {
        isRoundActive = roundActive;
    }

    public GameLogic() {
         isGameActive = true;
         isRoundActive = true;
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
