package ProjectDoge.StudentSoup.exception.school;

public class SchoolNameNotSentException extends RuntimeException {
    public SchoolNameNotSentException() {
        super();
    }

    public SchoolNameNotSentException(String message) {
        super(message);
    }

    public SchoolNameNotSentException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchoolNameNotSentException(Throwable cause) {
        super(cause);
    }

    protected SchoolNameNotSentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
