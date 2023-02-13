package ProjectDoge.StudentSoup.exception.file;

public class FileExtNotMatchException extends RuntimeException {
    public FileExtNotMatchException() {
        super();
    }

    public FileExtNotMatchException(String message) {
        super(message);
    }

    public FileExtNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileExtNotMatchException(Throwable cause) {
        super(cause);
    }

    protected FileExtNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
