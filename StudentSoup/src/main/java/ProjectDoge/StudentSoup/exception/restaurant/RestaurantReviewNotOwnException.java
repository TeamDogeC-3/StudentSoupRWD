package ProjectDoge.StudentSoup.exception.restaurant;

public class RestaurantReviewNotOwnException extends RuntimeException {

    public RestaurantReviewNotOwnException() {
        super();
    }

    public RestaurantReviewNotOwnException(String message) {
        super(message);
    }

    public RestaurantReviewNotOwnException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestaurantReviewNotOwnException(Throwable cause) {
        super(cause);
    }

    protected RestaurantReviewNotOwnException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
