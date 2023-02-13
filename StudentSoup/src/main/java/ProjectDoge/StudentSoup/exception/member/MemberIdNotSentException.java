package ProjectDoge.StudentSoup.exception.member;

public class MemberIdNotSentException extends RuntimeException {
    public MemberIdNotSentException() {
        super();
    }

    public MemberIdNotSentException(String message) {
        super(message);
    }

    public MemberIdNotSentException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberIdNotSentException(Throwable cause) {
        super(cause);
    }

    protected MemberIdNotSentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
