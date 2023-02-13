package ProjectDoge.StudentSoup.exhandler.advice;

import ProjectDoge.StudentSoup.exception.department.DepartmentIdNotSentException;
import ProjectDoge.StudentSoup.exception.department.DepartmentNotFoundException;
import ProjectDoge.StudentSoup.exception.member.MemberNotFoundException;
import ProjectDoge.StudentSoup.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DepartmentAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DepartmentNotFoundException.class)
    public ErrorResult departmentNotFoundHandler(DepartmentNotFoundException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("DepartmentNotFound", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DepartmentIdNotSentException.class)
    public ErrorResult departmentIdNotSentHandler(DepartmentIdNotSentException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("DepartmentIdNotSentException", e.getMessage());
    }
}
