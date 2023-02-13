package ProjectDoge.StudentSoup.exception.member;

public class MemberNotMatchIdPwdException extends RuntimeException {
    public MemberNotMatchIdPwdException() {
        super();
    }

    public MemberNotMatchIdPwdException(String message) {
        super(message);
    }

    public MemberNotMatchIdPwdException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberNotMatchIdPwdException(Throwable cause) {
        super(cause);
    }

    protected MemberNotMatchIdPwdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
