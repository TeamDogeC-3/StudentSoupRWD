package ProjectDoge.StudentSoup.exception.member;

public class MemberValidationException extends RuntimeException {
    public MemberValidationException() {
        super();
    }

    public MemberValidationException(String message) {
        super(message);
    }

    public MemberValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberValidationException(Throwable cause) {
        super(cause);
    }

    protected MemberValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
