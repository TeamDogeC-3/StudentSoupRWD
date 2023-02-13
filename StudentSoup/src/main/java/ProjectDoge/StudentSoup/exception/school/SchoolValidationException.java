package ProjectDoge.StudentSoup.exception.school;

public class SchoolValidationException extends RuntimeException{
    public SchoolValidationException() {
        super();
    }

    public SchoolValidationException(String message) {
        super(message);
    }

    public SchoolValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchoolValidationException(Throwable cause) {
        super(cause);
    }

    protected SchoolValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
