package ProjectDoge.StudentSoup.exhandler.advice;

import ProjectDoge.StudentSoup.exception.restaurant.*;
import ProjectDoge.StudentSoup.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestaurantMenuAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RestaurantMenuValidationException.class)
    public ErrorResult restaurantMenuValidationHandler(RestaurantMenuValidationException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("RestaurantMenuValidation",e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RestaurantMenuNotFoundException.class)
    public ErrorResult restaurantMenuNotFoundHandler(RestaurantMenuNotFoundException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("RestaurantMenuNotFound", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RestaurantMenuIdNotSentException.class)
    public ErrorResult restaurantMenuIdNotSentHandler(RestaurantMenuIdNotSentException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("RestaurantMenuIdNotSent",e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RestaurantStarLikedMoreThanFiveException.class)
    public ErrorResult RestaurantStarLikedMoreThanFiveHandler(RestaurantStarLikedMoreThanFiveException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("RestaurantStarLikedMoreThanFive",e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RestaurantReviewContentLessThanFiveException.class)
    public ErrorResult RestaurantReviewContentLessThanFiveHandler(RestaurantReviewContentLessThanFiveException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("RestaurantReviewContentLessThanFive",e.getMessage());
    }
}
