package ProjectDoge.StudentSoup.exception.member;

public class MemberEmailNotFoundException extends RuntimeException {
    public MemberEmailNotFoundException() {
        super();
    }

    public MemberEmailNotFoundException(String message) {
        super(message);
    }

    public MemberEmailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberEmailNotFoundException(Throwable cause) {
        super(cause);
    }

    protected MemberEmailNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
