package ProjectDoge.StudentSoup.exception.restaurant;

public class RestaurantMenuNotFoundException extends RuntimeException{

    public RestaurantMenuNotFoundException() {
        super();
    }

    public RestaurantMenuNotFoundException(String message){
        super(message);
    }

    public RestaurantMenuNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestaurantMenuNotFoundException(Throwable cause) {
        super(cause);
    }

    protected RestaurantMenuNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
