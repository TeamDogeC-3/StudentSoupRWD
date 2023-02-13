package ProjectDoge.StudentSoup.exhandler.advice;

import ProjectDoge.StudentSoup.exception.member.MemberValidationException;
import ProjectDoge.StudentSoup.exception.restaurant.*;
import ProjectDoge.StudentSoup.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestaurantAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ErrorResult restaurantNotFoundHandler(RestaurantNotFoundException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("RestaurantNotFound",e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RestaurantValidationException.class)
    public ErrorResult restaurantValidationHandler(RestaurantValidationException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("RestaurantValidation", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RestaurantLikeValidationException.class)
    public ErrorResult restaurantLikeValidationHandler(RestaurantLikeValidationException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("RestaurantLikeValidation", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RestaurantIdNotSentException.class)
    public ErrorResult restaurantIdNotSentHandler(RestaurantIdNotSentException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("RestaurantIdNotSent", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RestaurantNotMatchException.class)
    public ErrorResult restaurantNotMatchHandler(RestaurantNotMatchException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("RestaurantNotMatch", e.getMessage());
    }
}
