package ProjectDoge.StudentSoup.exception.member;

public class MemberIdOutOfRangeException extends RuntimeException {

    public MemberIdOutOfRangeException() {
        super();
    }

    public MemberIdOutOfRangeException(String message) {
        super(message);
    }

    public MemberIdOutOfRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberIdOutOfRangeException(Throwable cause) {
        super(cause);
    }

    protected MemberIdOutOfRangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
