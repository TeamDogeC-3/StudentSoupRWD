package ProjectDoge.StudentSoup.exception.department;

public class DepartmentIdNotSentException extends RuntimeException {
    public DepartmentIdNotSentException() {
        super();
    }

    public DepartmentIdNotSentException(String message) {
        super(message);
    }

    public DepartmentIdNotSentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepartmentIdNotSentException(Throwable cause) {
        super(cause);
    }

    protected DepartmentIdNotSentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
