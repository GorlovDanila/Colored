package core;

public class GameLogic {
    private String correctWord;
    private boolean isGameActive;
    private boolean isRoundActive;

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
