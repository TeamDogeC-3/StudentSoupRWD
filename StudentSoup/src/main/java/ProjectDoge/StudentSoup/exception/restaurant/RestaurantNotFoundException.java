package ProjectDoge.StudentSoup.exception.restaurant;

public class RestaurantNotFoundException extends RuntimeException{

    public RestaurantNotFoundException(String message){
        super(message);
    }
}
