package ProjectDoge.StudentSoup.exception.restaurant;

public class RestaurantIdNotSentException extends RuntimeException {
    public RestaurantIdNotSentException() {
        super();
    }

    public RestaurantIdNotSentException(String message) {
        super(message);
    }

    public RestaurantIdNotSentException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestaurantIdNotSentException(Throwable cause) {
        super(cause);
    }

    protected RestaurantIdNotSentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
