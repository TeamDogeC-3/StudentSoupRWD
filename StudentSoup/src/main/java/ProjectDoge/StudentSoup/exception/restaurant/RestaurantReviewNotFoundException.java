package ProjectDoge.StudentSoup.exception.restaurant;

public class RestaurantReviewNotFoundException extends RuntimeException {
    public RestaurantReviewNotFoundException() {
        super();
    }

    public RestaurantReviewNotFoundException(String message) {
        super(message);
    }

    public RestaurantReviewNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestaurantReviewNotFoundException(Throwable cause) {
        super(cause);
    }

    protected RestaurantReviewNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
