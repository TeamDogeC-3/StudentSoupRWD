package ProjectDoge.StudentSoup.exception.restaurant;

public class RestaurantStarLikedMoreThanFiveException extends RuntimeException {
    public RestaurantStarLikedMoreThanFiveException() {
        super();
    }

    public RestaurantStarLikedMoreThanFiveException(String message) {
        super(message);
    }

    public RestaurantStarLikedMoreThanFiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestaurantStarLikedMoreThanFiveException(Throwable cause) {
        super(cause);
    }

    protected RestaurantStarLikedMoreThanFiveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
