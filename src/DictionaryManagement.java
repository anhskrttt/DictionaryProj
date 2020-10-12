import java.io.*;
import java.util.List;
import java.util.Scanner;

public class DictionaryManagement {
    private static final String IN_PATHNAME = "src\\resources\\vocabImport.txt";
    private static final String OUT_PATHNAME = "src\\resources\\vocabExport.txt";

    Dictionary dic = new Dictionary();
    Scanner sc = new Scanner(System.in);

    public int parseInteger() {
        System.out.print(">>> Enter a number: ");
        int n;
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        try {
            n = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("\t~WARNING: Invalid integer number! Please try again!");
            return -1;
        }

        return n;
    }

    public void insertFromCommandline() {
        int n = parseInteger();
        if (n <= 0) return;

        System.out.printf(">>> Enter %d vocab(s): ", n);
        System.out.println("\t~REMINDER: Vocab and its meaning MUST be on the SAME LINE, separated by ONE TAB!");

        for (int i = 0; i < n; ++i){
            String[] parts = sc.nextLine().toLowerCase().trim().split("\t");

            if (parts.length == 2) {
                parts[0].toLowerCase();
                parts[1].toLowerCase();
                dic.addWord(new Word(parts[0], parts[1]));
            } else System.out.println("~WARNING: Invalid input!");
        }

    }

    public void insertFromFile() {
        try {
            File objFile = new File(IN_PATHNAME);
            Scanner sc = new Scanner(objFile);

            int index = 0;
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().toLowerCase().trim().split("\t");
                parts[0].toLowerCase();
                parts[1].toLowerCase();


                dic.addWord(new Word(parts[0], parts[1]));
            }
            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("\t~WARNING: An error occurred! File not found!");
        }
    }

    public void dictionaryLookup() {
        System.out.print(">>> Enter a vocab for looking definition up: ");
        Scanner sc = new Scanner(System.in);

        /* Reformat input vocab: remove leading/trailing whitespaces, which equivalents to String.trim();. */
        String s = sc.nextLine().toLowerCase().replaceAll("^\\s*|\\s*$", "");
        dic.searchMeaning(s);
    }

    public void dictionaryExportToFile() {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(OUT_PATHNAME));
            List<Word> list = dic.getMylist();

            list.forEach(w -> {
                try {
                    br.write(w.getWord_target() + "\t" + w.getWord_explain() + '\n');
                } catch (IOException e) {
                    System.out.println("\t~WARNING: An error occurred! File not found!");
                }
            });

            br.close();

        } catch (IOException e) {
            System.out.println("\t~WARNING: An error occurred! File not found!");
        }
    }

    public void updateWords() {
        int n = parseInteger();
        if (n <= 0) return;

        System.out.printf(">>> Enter %d word(s): ", n);
        System.out.println("\t~REMINDER: Vocab and meaning MUST be on the SAME LINE, separated by ONE TAB!");

        for (int i = 0; i < n; ++i){
            dic.updateMeaning(sc.nextLine().toLowerCase().trim());
        }
    }

    public void deleteWords() {
        int n = parseInteger();
        if (n <= 0) return;

        System.out.printf(">>> Enter %d word(s): ", n);
        System.out.println("\t~REMINDER: Each vocab lies on ONE LINE!");

        for (int i = 0; i < n; ++i) {
            dic.deleteWord(sc.nextLine().toLowerCase().trim());
        }
    }

}
