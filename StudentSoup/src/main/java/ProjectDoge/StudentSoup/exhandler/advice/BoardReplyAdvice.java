package ProjectDoge.StudentSoup.exhandler.advice;

import ProjectDoge.StudentSoup.exception.boardreply.*;
import ProjectDoge.StudentSoup.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BoardReplyAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BoardReplyContentNullException.class)
    public ErrorResult boardReplyContentNullHandler(BoardReplyContentNullException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BoardReplyContentNull", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BoardReplyNotFoundException.class)
    public ErrorResult boardReplyNotFoundHandler(BoardReplyNotFoundException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BoardReplyNotFound", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BoardReplyIdNotSentException.class)
    public ErrorResult boardReplyIdNotSentHandler(BoardReplyIdNotSentException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BoardReplyIdNotSent", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BoardReplyNotOwnException.class)
    public ErrorResult boardReplyNotOwnHandler(BoardReplyNotOwnException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BoardReplyNotOwn", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BoardReplyContentOutOfRangeException.class)
    public ErrorResult BoardReplyContentOutOfRangeHandler(BoardReplyContentOutOfRangeException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BoardReplyContentOutOfRange", e.getMessage());
    }

}
