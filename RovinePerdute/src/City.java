import java.util.List;

public class City {
    private int id;
    private String name;
    private int latitude;
    private int longitude;
    private int altitude;
    private List<City> connections;

    public List<City> getConnections() {
        return connections;
    }

    public void setConnections(List<City> connections) {
        this.connections = connections;
    }

    public City(int id, String name, int latitude, int longitude, int altitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getlatitude() {
        return latitude;
    }

    public int getlongitude() {
        return longitude;
    }

    public int getaltitude() {
        return altitude;
    }
    
}
