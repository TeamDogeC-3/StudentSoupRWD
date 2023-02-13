package ProjectDoge.StudentSoup.exception.school;

public class SchoolIdNotSentException extends RuntimeException {
    public SchoolIdNotSentException() {
        super();
    }

    public SchoolIdNotSentException(String message) {
        super(message);
    }

    public SchoolIdNotSentException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchoolIdNotSentException(Throwable cause) {
        super(cause);
    }

    protected SchoolIdNotSentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
