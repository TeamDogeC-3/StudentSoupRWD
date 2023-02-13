package ProjectDoge.StudentSoup.exhandler.advice;

import ProjectDoge.StudentSoup.exception.page.PagingLimitEqualsZeroException;
import ProjectDoge.StudentSoup.exception.page.PagingOffsetMoreThanTotalPageException;
import ProjectDoge.StudentSoup.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class PagingAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PagingLimitEqualsZeroException.class)
    public ErrorResult PagingLimitHandler(PagingLimitEqualsZeroException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("PagingLimitEqualsZero", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PagingOffsetMoreThanTotalPageException.class)
    public ErrorResult PagingOffsetHandler(PagingOffsetMoreThanTotalPageException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("PagingOffsetMoreThanTotalPage", e.getMessage());
    }
}
