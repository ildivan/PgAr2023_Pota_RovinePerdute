import com.github.lalyos.jfiglet.FigletFont;
import it.kibo.fp.lib.Menu;
import it.kibo.fp.lib.InputData;

/**
 * The <strong>LostRuinsMenu</strong> class contains all the static methods
 * for displaying contents to the user or for read the user input.
 */
public class LostRuinsMenu {

    /**
     * Menu for displaying the main functions.
     * @return A number representing the user choice.
     */
    public static int mainMenu() {
        Menu.clearConsole();
        welcome();
        String[] entries = { Literals.INPUT_FILE_PATH, Literals.SELECT_ONE_XML_FILE};
        Menu tamaMenu = new Menu(Literals.RUINS_MENU, entries, true, true, true);
        return tamaMenu.choose();
    }

    /**
     * Menu for displaying the preset paths.
     * @return A number representing the user choice.
     */
    public static int presetMenu() {
        Menu.clearConsole();
        String[] entries = { "5 cities", "13 cities", "50 cities", "200 cities", "2000 cities", "10000 cities" };
        Menu presetMenu = new Menu(Literals.SELECT_ONE_PRESET, entries, true, true, false);
        return presetMenu.choose();
    }

    /**
     * Function that read the file path input by the user.
     */
    public static void inputFilePath() {
        String path = InputData.readNonEmptyString(Literals.INPUT_FILE_PATH_1, false);
        Main.startPathFinding(path, null);
    }

    /**
     * Shows a message to indicate to the user that the program is proceeding with the algorithm to find the best route.
     * @param numOfCities The number of cities that are going to be calculated.
     */
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

    /**
     * Prints the welcome ASCII art text.
     */
    public static void welcome() {
        try {
            System.out.println(FigletFont.convertOneLine(Literals.WELCOME_TO));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
