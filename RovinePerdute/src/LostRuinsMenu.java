import com.github.lalyos.jfiglet.FigletFont;
import it.kibo.fp.lib.Menu;
import it.kibo.fp.lib.InputData;

public class LostRuinsMenu {

    public static int mainMenu() {
        Menu.clearConsole();
        welcome();
        String[] entries = { "Input file path", "Select one of the default XML files" };
        Menu tamaMenu = new Menu("Ruins' Menu", entries, true, true, true);
        return tamaMenu.choose();
    }

    public static int presetMenu() {
        Menu.clearConsole();
        String[] entries = { "5 cities", "12 cities", "50 cities", "200 cities", "2000 cities", "10000 cities" };
        Menu presetMenu = new Menu("Select one of the following presets", entries, true, true, false);
        return presetMenu.choose();
    }

    public static void inputFilePath() {
        String path = InputData.readNonEmptyString("Input file path: ", false);
        Main.calculatePath(path);
    }

    public static void loading(int numOfCities) {
        try {
            if (numOfCities <= 2000)
                Menu.loadingMessage(String.format("We are processing %d cities", numOfCities));
            else
                System.out.printf("We are processing %d cities, this could take a while...", numOfCities);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
