package ProjectDoge.StudentSoup.exception.member;

public class MemberRegexException extends RuntimeException {

    public MemberRegexException() {
        super();
    }

    public MemberRegexException(String message) {
        super(message);
    }

    public MemberRegexException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberRegexException(Throwable cause) {
        super(cause);
    }

    protected MemberRegexException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
