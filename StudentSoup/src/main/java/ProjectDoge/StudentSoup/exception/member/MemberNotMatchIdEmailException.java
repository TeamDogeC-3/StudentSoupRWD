package ProjectDoge.StudentSoup.exception.member;

public class MemberNotMatchIdEmailException extends RuntimeException {
    public MemberNotMatchIdEmailException() {
        super();
    }

    public MemberNotMatchIdEmailException(String message) {
        super(message);
    }

    public MemberNotMatchIdEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberNotMatchIdEmailException(Throwable cause) {
        super(cause);
    }

    protected MemberNotMatchIdEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
