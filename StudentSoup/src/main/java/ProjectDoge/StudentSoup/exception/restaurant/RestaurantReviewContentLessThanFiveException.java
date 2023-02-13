package ProjectDoge.StudentSoup.exception.restaurant;

public class RestaurantReviewContentLessThanFiveException extends RuntimeException {
    public RestaurantReviewContentLessThanFiveException() {
        super();
    }

    public RestaurantReviewContentLessThanFiveException(String message) {
        super(message);
    }

    public RestaurantReviewContentLessThanFiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestaurantReviewContentLessThanFiveException(Throwable cause) {
        super(cause);
    }

    protected RestaurantReviewContentLessThanFiveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
