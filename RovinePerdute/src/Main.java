import java.util.List;

public class Main {
    public static void main(String[] args) {
        try{
            List<City> cities = XMLHandler.getCities("RovinePerdute/test_file/PgAr_Map_2000.xml");
            PathFinder p1 = new PathFinder(cities.get(0),cities.get(cities.size()-1),new Tonatiuh());
            PathFinder p2 = new PathFinder(cities.get(0),cities.get(cities.size()-1),new Metztli());
            XMLHandler.writeOutput(p1,p2,"RovinePerdute/output/Routes.xml");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}