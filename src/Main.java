import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    static public void main(String[] args) {
        DictionaryCommandline dc = new DictionaryCommandline();

        int option = -1;
        do {
            showMenu();

            try {
                String s = sc.nextLine();
                option = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("\t~WARNING: Invalid choice! Please try again!");
                continue;
            }

            switch (option) {
                case 1:
                    dc.dictionarySearcher();
                    break;
                case 2:
                    dc.dictionaryLookup();
                    break;
                case 3:
                    dc.showAllWords();
                    break;
                case 4:
                    dc.dictionaryExportToFile();
                    break;
                case 5:
                    dc.insertFromFile();
                    break;
                case 6:
                    dc.insertFromCommandline();
                    break;
                case 7:
                    dc.updateWords();
                    break;
                case 8:
                    dc.deleteWords();
                    break;
                default:
            }

        } while (option != 0);
    }

    public static void showMenu() {
		System.out.println(" +=====================+======================+====================+");
        System.out.println(" | 0. Quit             | 3. Show words        | 6. Add words       |");
        System.out.println(" +---------------------+----------------------+--------------------+");
        System.out.println(" | 1. Search phrase    | 4. Export to file    | 7. Update words    |");
        System.out.println(" +---------------------+----------------------+--------------------+");
        System.out.println(" | 2. Look meaning up  | 5. Import from file  | 8. Delete words    |");
        System.out.println(" +=====================+=====================+=====================+");
        System.out.print(">>> Enter an option: ");
    }

}
