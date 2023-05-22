public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException() {
        this("City not found!");
    }

    public CityNotFoundException(String message) {
        super(message);
    }
}
