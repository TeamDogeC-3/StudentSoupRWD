package ProjectDoge.StudentSoup.exhandler.advice;

import ProjectDoge.StudentSoup.exception.member.MemberNotFoundException;
import ProjectDoge.StudentSoup.exception.school.SchoolIdNotSentException;
import ProjectDoge.StudentSoup.exception.school.SchoolNameNotSentException;
import ProjectDoge.StudentSoup.exception.school.SchoolNotFoundException;
import ProjectDoge.StudentSoup.exception.school.SchoolValidationException;
import ProjectDoge.StudentSoup.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class SchoolAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SchoolIdNotSentException.class)
    public ErrorResult schoolIdNotSentHandler(SchoolIdNotSentException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("SchoolIdNotSent", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SchoolNotFoundException.class)
    public ErrorResult schoolNotFoundHandler(SchoolNotFoundException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("SchoolNotFound", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SchoolValidationException.class)
    public ErrorResult schoolValidationHandler(SchoolValidationException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("SchoolValidation", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SchoolNameNotSentException.class)
    public ErrorResult schoolValidationHandler(SchoolNameNotSentException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("SchoolNameNotSent", e.getMessage());
    }
}
