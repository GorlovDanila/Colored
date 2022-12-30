package core;

public class GameLogic {
    private String correctWord;
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
