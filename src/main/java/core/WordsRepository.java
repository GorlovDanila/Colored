package core;

import java.util.List;

public class WordsRepository {
    private List<String> words = List.of("Лягушка", "Хорват", "Англия", "Футбол", "Свисток");

    public List<String> getWords() {
        return words;
    }

//    public int wordsListSize() {
//        return words.size();
//    }
}
