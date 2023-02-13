package ProjectDoge.StudentSoup.exhandler.advice;

import ProjectDoge.StudentSoup.exception.board.*;
import ProjectDoge.StudentSoup.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BoardAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BoardNotFoundException.class)
    public ErrorResult BoardNotFoundHandler(BoardNotFoundException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BoardNotFound", e.getMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BoardIdNotSentException.class)
    public ErrorResult BoardIdNotSentHandler(BoardIdNotSentException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BoardIdNotSent", e.getMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BoardSearchDataNotSentException.class)
    public  ErrorResult BoardSearchDataNotSentHandler(BoardSearchDataNotSentException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BoardSearchDataNotSent", e.getMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BoardNotOwnMemberException.class)
    public ErrorResult BoardNotOwnMemberHandler(BoardNotOwnMemberException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BoardNotOwnMember",e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BoardNotQualifiedException.class)
    public ErrorResult BoardNotQualifiedHandler(BoardNotQualifiedException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BoardNotQualified",e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BoardContentOutOfRangeException.class)
    public ErrorResult BoardContentOutOfRangeHandler(BoardContentOutOfRangeException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BoardContentOutOfRange",e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BoardTitleOutOfRangeException.class)
    public ErrorResult BoardTitleOutOfRangeHandler(BoardTitleOutOfRangeException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BoardTitleOutOfRange",e.getMessage());
    }

}
