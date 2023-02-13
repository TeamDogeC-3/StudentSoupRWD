package ProjectDoge.StudentSoup.exception.restaurant;

public class RestaurantReviewIdNotSentException extends RuntimeException {
    public RestaurantReviewIdNotSentException() {
        super();
    }

    public RestaurantReviewIdNotSentException(String message) {
        super(message);
    }

    public RestaurantReviewIdNotSentException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestaurantReviewIdNotSentException(Throwable cause) {
        super(cause);
    }

    protected RestaurantReviewIdNotSentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
