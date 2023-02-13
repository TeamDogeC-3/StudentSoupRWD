package ProjectDoge.StudentSoup.exhandler.advice;

import ProjectDoge.StudentSoup.exception.admin.MemberClassificationNotAdminException;
import ProjectDoge.StudentSoup.exception.board.BoardNotFoundException;
import ProjectDoge.StudentSoup.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class AdminAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberClassificationNotAdminException.class)
    public ErrorResult MemberClassificationNotAdminHandler(MemberClassificationNotAdminException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("MemberClassificationNotAdmin", e.getMessage());
    }
}
