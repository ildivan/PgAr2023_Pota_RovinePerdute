import it.kibo.fp.lib.Menu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Menu.clearConsole();
        // If-else statement for differentiating command-line session from the session with a Menu interaction.
        if (args.length == 0) {
            do {
                switch (LostRuinsMenu.mainMenu()) {
                    case 1 -> LostRuinsMenu.inputFilePath();
                    case 2 -> selectPreset();
                    case 0 -> {
                        return;
                    }
                }
                Menu.clearConsole();
            } while (true);
        } else {
            String directory = null;
            ArrayList<String> listOfFiles = new ArrayList<>();
            boolean fileList = false;
            try {
                // Analyze all commands given in command-line.
                // Allowed commands: --help, -h, --output, -o, --files, -f
                for (int i = 0; i < args.length; i++) {
                    if (args[i].startsWith("-")) {
                        switch (args[i].substring(args[i].lastIndexOf("-") + 1)) {
                            case "o":
                            case "output":
                                fileList = false;
                                if (directory == null)
                                    directory = args[++i];
                                else
                                    throw new IllegalArgumentException(Literals.OUTPUT_DIRECTORY_MULTIPLE_TIMES);
                                break;
                            case "f":
                            case "files":
                                fileList = true;
                                break;
                            case "h":
                            case "help":
                                if (args.length == 1) {
                                    showHelp();
                                    break;
                                }
                            default:
                                throw new IllegalArgumentException("Invalid option: " + args[i].substring(args[i].lastIndexOf("-") + 1));
                        }
                        continue;
                    }
                    if (fileList)
                        listOfFiles.add(args[i]);
                    else
                        throw new IllegalArgumentException(Literals.SPECIFY_F_COMMAND);
                }

                // Check if output is really a folder
                if (directory != null && (!new File(directory).isDirectory() || !directory.endsWith("\\")))
                    throw new IllegalArgumentException(String.format(Literals.PATH_IS_NOT_A_DIRECTORY, directory));

                // Run pathfinding for all XML files given in input
                for (String path : listOfFiles)
                    startPathFinding(path, directory == null ? "Output/" : directory);

            } catch (IllegalArgumentException i) {
                System.err.println(i.getMessage());
            }
        }
    }

    /**
     * Run the pathfinding algorithm and call the function for writing the output file
     *
     * @param sourcePath The complete filepath
     * @param outputPath The folder where store the output file
     */
    public static void startPathFinding(String sourcePath, String outputPath) {
        try {
            List<City> cities = XMLHandler.getCities(sourcePath);
            PathFinder p1 = new PathFinder(cities.get(0), cities.get(cities.size() - 1), new Tonatiuh());
            PathFinder p2 = new PathFinder(cities.get(0), cities.get(cities.size() - 1), new Metztli());
            XMLHandler.writeOutput(p1, p2, String.format(outputPath == null ? "RovinePerdute/output/Routes_%s" : outputPath + "Routes_%s", XMLHandler.getXMLFileName(sourcePath)));
            Menu.clearConsole();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Switch-case statement for calling one of the XML presets
     */
    public static void selectPreset() {
        switch (LostRuinsMenu.presetMenu()) {
            case 1 -> startPathFinding("RovinePerdute/test_file/PgAr_Map_5.xml", null);
            case 2 -> startPathFinding("RovinePerdute/test_file/PgAr_Map_13.xml", null);
            case 3 -> startPathFinding("RovinePerdute/test_file/PgAr_Map_50.xml", null);
            case 4 -> startPathFinding("RovinePerdute/test_file/PgAr_Map_200.xml", null);
            case 5 -> startPathFinding("RovinePerdute/test_file/PgAr_Map_2000.xml", null);
            case 6 -> startPathFinding("RovinePerdute/test_file/PgAr_Map_10000.xml", null);
            case 0 -> {
                return;
            }
        }
    }

    /**
     * Displays the commands for interaction with the command-line
     */
    public static void showHelp() {
        System.out.println(
                "Usage: PgAr2023_Pota_RovinePerdute.jar (FILE_PATH)... [OUTPUT_FOLDER]\n\n"
                        + "Option\tLong option\tMeaning\n"
                        + "  -h\t--help\t\tShows this help message\n"
                        + "  -f\t--files\t\tFollowed by all XML files to analyze\n"
                        + "  -o\t--output\tDirectory for output files\n");
    }
}