package ProjectDoge.StudentSoup.exhandler.advice;

import ProjectDoge.StudentSoup.exception.file.FileExtNotMatchException;
import ProjectDoge.StudentSoup.exception.file.ImageConvertException;
import ProjectDoge.StudentSoup.exception.file.ImageFileNotFoundException;
import ProjectDoge.StudentSoup.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class FileAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FileExtNotMatchException.class)
    public ErrorResult FileExtNotMatchHandler(FileExtNotMatchException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("FileExtNotMatch", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ImageFileNotFoundException.class)
    public ErrorResult ImageFileNotFoundHandler(ImageFileNotFoundException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("ImageFileNotFound", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ImageConvertException.class)
    public ErrorResult ImageConvertHandler(ImageConvertException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("ImageConvert", e.getMessage());
    }
}
