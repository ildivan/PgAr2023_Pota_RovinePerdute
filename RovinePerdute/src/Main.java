import java.util.List;

public class Main {
    public static void main(String[] args) {
        try{
            List<City> cities = XMLHandler.getCities("RovinePerdute/test_file/PgAr_Map_50.xml");
            PathFinder fnd = new PathFinder(cities.get(0),cities.get(cities.size()-1),new Metztli());
            System.out.println(fnd.getOptimalRoute());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}