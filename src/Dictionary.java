import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    private final List<Word> mylist = new ArrayList<>();

    public List<Word> getMylist() {
        return mylist;
    }

    public void addWord(Word w) {
        mylist.add(w);
    }

    public void showWords() {
        short[] i = {1};
        mylist.forEach(w -> {
            System.out.printf("%-5d", i[0]++);
            System.out.println(w);
        });
    }

    public void searchMeaning(String vocab) {
        for (Word w : mylist) {
            if (vocab.equals(w.getWord_target())) {
                System.out.printf("\"%s\" means \"%s\".\n", w.getWord_target(), w.getWord_explain());
                return;
            }
        }
        System.out.println("\t~NOTE: 404 not found!");
    }

    public List<Word> searchWord(String phrase) {
        List<Word> resultList = new ArrayList<>();

        for (Word w : mylist) {
            String vocab = w.getWord_target();
            if (vocab.startsWith(phrase)) {
                resultList.add(w);
            }
        }
        return resultList;
    }

    public void updateMeaning(String string) {
        String[] parts = string.split("\t");
        parts[0].toLowerCase();
        parts[1].toLowerCase();

        // using basic loop.
        for (Word w : mylist) {
            if (parts[0].equals(w.getWord_target())) {
                w.setWord_explain(parts[1]);
            }
        }
    }

    public void deleteWord(String vocab) {
        /*
          Delete all vocabs that contain the input phrase.
          Therefore deleting a vocab will be exacter if phrase is typed sufficently equivalent to a vocab.
         */

        // use lambda in Java 8.
        mylist.removeIf(o -> o.getWord_target().equals(vocab));
    }
}
