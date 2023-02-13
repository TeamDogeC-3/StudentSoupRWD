package ProjectDoge.StudentSoup.exception.restaurant;

public class RestaurantMenuIdNotSentException extends RuntimeException {

    public RestaurantMenuIdNotSentException() {
        super();
    }

    public RestaurantMenuIdNotSentException(String message) {
        super(message);
    }

    public RestaurantMenuIdNotSentException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestaurantMenuIdNotSentException(Throwable cause) {
        super(cause);
    }

    protected RestaurantMenuIdNotSentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

    }
}
