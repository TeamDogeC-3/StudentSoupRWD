package ProjectDoge.StudentSoup.exception.restaurant;

public class RestaurantNotMatchException extends RuntimeException {
    public RestaurantNotMatchException() {
        super();
    }

    public RestaurantNotMatchException(String message) {
        super(message);
    }

    public RestaurantNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestaurantNotMatchException(Throwable cause) {
        super(cause);
    }

    protected RestaurantNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
