import java.util.List;

public class Main {
    public static void main(String[] args) {
        do {
            switch (LostRuinsMenu.mainMenu()) {
                case 1 -> LostRuinsMenu.inputFilePath();
                case 2 -> selectPreset();
                case 0 -> {
                    return;
                }
            }
        } while (true);
    }

    public static void calculatePath(String sourcePath) {
        try {
            List<City> cities = XMLHandler.getCities(sourcePath);
            PathFinder p1 = new PathFinder(cities.get(0), cities.get(cities.size() - 1), new Tonatiuh());
            PathFinder p2 = new PathFinder(cities.get(0), cities.get(cities.size() - 1), new Metztli());
            XMLHandler.writeOutput(p1, p2, String.format("RovinePerdute/output/Routes_%s", XMLHandler.getXMLFileName(sourcePath)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void selectPreset() {
        switch (LostRuinsMenu.presetMenu()) {
            case 1 -> calculatePath("RovinePerdute/test_file/PgAr_Map_5.xml");
            case 2 -> calculatePath("RovinePerdute/test_file/PgAr_Map_13.xml");
            case 3 -> calculatePath("RovinePerdute/test_file/PgAr_Map_50.xml");
            case 4 -> calculatePath("RovinePerdute/test_file/PgAr_Map_200.xml");
            case 5 -> calculatePath("RovinePerdute/test_file/PgAr_Map_2000.xml");
            case 6 -> calculatePath("RovinePerdute/test_file/PgAr_Map_10000.xml");
            case 0 -> {
                return;
            }
        }
    }
}