import java.util.List;
import java.util.Scanner;

public class DictionaryCommandline extends DictionaryManagement {
    public void showAllWords() {
        System.out.format("%-5s%-22s%s\n", "No", "| English", "| Vietnamese");
        dic.showWords();
    }

    public void dictionaryBasic() {
        insertFromCommandline();
        showAllWords();
    }

    public void dictionaryAdvanced() {
        insertFromFile();
        showAllWords();
        dictionaryLookup();
    }

    public void dictionarySearcher() {
        System.out.print(">>> Enter a phrase for searching: ");
        Scanner sc = new Scanner(System.in);

        /* Reformat vocab: remove leading/trailing whitespaces and capitalize first letter. */
        String phrase = sc.nextLine().toLowerCase().replaceAll("^\\s*|\\s*$", "");

        List<Word> list = dic.searchWord(phrase);
        int num = list.size();
        String result = (num > 1) ? (num + " results:") : ((num == 1) ? (num + " result:") : "No results!");
        System.out.println(result);

        list.forEach(w->System.out.println(w.getWord_target()));
    }


}
