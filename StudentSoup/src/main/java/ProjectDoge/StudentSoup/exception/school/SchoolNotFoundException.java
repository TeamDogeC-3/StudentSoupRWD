package ProjectDoge.StudentSoup.exception.school;

public class SchoolNotFoundException extends RuntimeException {
    public SchoolNotFoundException() {
        super();
    }

    public SchoolNotFoundException(String message) {
        super(message);
    }

    public SchoolNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchoolNotFoundException(Throwable cause) {
        super(cause);
    }

    protected SchoolNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
