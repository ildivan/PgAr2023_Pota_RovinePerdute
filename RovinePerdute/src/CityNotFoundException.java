public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException() {
        this(Literals.CITY_NOT_FOUND);
    }

    public CityNotFoundException(String message) {
        super(message);
    }
}
