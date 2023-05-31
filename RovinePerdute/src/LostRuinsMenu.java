import com.github.lalyos.jfiglet.FigletFont;
import it.kibo.fp.lib.Menu;
import it.kibo.fp.lib.InputData;

public class LostRuinsMenu {

    public static int mainMenu() {
        Menu.clearConsole();
        welcome();
        String[] entries = { Literals.INPUT_FILE_PATH, Literals.SELECT_ONE_XML_FILE};
        Menu tamaMenu = new Menu(Literals.RUINS_MENU, entries, true, true, true);
        return tamaMenu.choose();
    }

    public static int presetMenu() {
        Menu.clearConsole();
        String[] entries = { "5 cities", "12 cities", "50 cities", "200 cities", "2000 cities", "10000 cities" };
        Menu presetMenu = new Menu(Literals.SELECT_ONE_PRESET, entries, true, true, false);
        return presetMenu.choose();
    }

    public static void inputFilePath() {
        String path = InputData.readNonEmptyString(Literals.INPUT_FILE_PATH_1, false);
        Main.startPathFinding(path);
    }

    public static void loading(int numOfCities) {
        try {
            if (numOfCities <= 2000)
                Menu.loadingMessage(String.format(Literals.PROCESSING_CITIES, numOfCities));
            else
                System.out.printf(Literals.PROCESSING_CITIES_TAKE_A_WHILE, numOfCities);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Prints the welcome ASCII art text.
    public static void welcome() {
        try {
            System.out.println(FigletFont.convertOneLine(Literals.WELCOME_TO));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
