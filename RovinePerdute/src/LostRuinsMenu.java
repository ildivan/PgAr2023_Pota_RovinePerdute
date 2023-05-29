import com.github.lalyos.jfiglet.FigletFont;
import it.kibo.fp.lib.Menu;
import it.kibo.fp.lib.InputData;

public class LostRuinsMenu {

    public static int mainMenu() {
        Menu.clearConsole();
        welcome();
        String[] entries = { "Input file path", "Select one of the default XML files", "Perform a test (Map 2000)" };
        Menu tamaMenu = new Menu("Tama Menu", entries, true, true, true);
        return tamaMenu.choose();
    }

    public static int presetMenu() {
        Menu.clearConsole();
        String[] entries = { "5 citta'", "12 citta'", "50 citta'", "200 citta'", "2000 citta'", "10000 citta'" };
        Menu presetMenu = new Menu("Select one of the following presets", entries, true, true, false);
        return presetMenu.choose();
    }

    public static void inputFilePath() {
        String path = InputData.readNonEmptyString("Input file path: ", false);
        Main.calculatePath(path);
    }

    // Prints the welcome ASCII art text.
    public static void welcome() {
        try {
            System.out.println(FigletFont.convertOneLine("Lost Ruins"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
