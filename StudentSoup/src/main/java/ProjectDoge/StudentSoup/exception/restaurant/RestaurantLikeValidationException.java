package ProjectDoge.StudentSoup.exception.restaurant;

public class RestaurantLikeValidationException extends RuntimeException {
    public RestaurantLikeValidationException() {
        super();
    }

    public RestaurantLikeValidationException(String message) {
        super(message);
    }

    public RestaurantLikeValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestaurantLikeValidationException(Throwable cause) {
        super(cause);
    }

    protected RestaurantLikeValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
