package ProjectDoge.StudentSoup.exhandler.advice;

import ProjectDoge.StudentSoup.exception.member.*;
import ProjectDoge.StudentSoup.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberNotFoundException.class)
    public ErrorResult memberNotFoundHandler(MemberNotFoundException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("MemberNotFound", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberValidationException.class)
    public ErrorResult memberValidationHandler(MemberValidationException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("MemberValidation", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberNotMatchIdPwdException.class)
    public ErrorResult memberCheckPasswordHandler(MemberNotMatchIdPwdException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("MemberNotMatchIdPwd", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberIdNotSentException.class)
    public ErrorResult memberIdNotSentHandler(MemberIdNotSentException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("MemberIdNotSent", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberEmailNotFoundException.class)
    public ErrorResult memberEmailNotFoundHandler(MemberEmailNotFoundException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("MemberEmailNotFound", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberNotMatchIdEmailException.class)
    public ErrorResult memberNotMatchIdEmailHandler(MemberNotMatchIdEmailException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("MemberNotMatchIdEmail", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberIdOutOfRangeException.class)
    public ErrorResult MemberIdOutOfRangeHandler(MemberIdOutOfRangeException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("MemberIdOutOfRange", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberNicknameOutOfRangeException.class)
    public ErrorResult MemberNicknameOutOfRangeHandler(MemberNicknameOutOfRangeException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("MemberNicknameOutOfRange", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberRegexException.class)
    public ErrorResult MemberRegexHandler(MemberRegexException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("MemberRegex", e.getMessage());
    }
}
