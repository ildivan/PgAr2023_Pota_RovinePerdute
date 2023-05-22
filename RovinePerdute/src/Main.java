import java.util.List;

public class Main {
    public static void main(String[] args) {
        try{
            List<City> cities = XMLHandler.getCities("RovinePerdute/test_file/PgAr_Map_50.xml");
            System.out.println(cities);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}