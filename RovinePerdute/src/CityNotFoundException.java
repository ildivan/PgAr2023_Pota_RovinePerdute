/**
 * Exception thrown if the city with id equal to the one given in input is not found.
 */
public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException() {
        this(Literals.CITY_NOT_FOUND);
    }

    public CityNotFoundException(String message) {
        super(message);
    }
}
