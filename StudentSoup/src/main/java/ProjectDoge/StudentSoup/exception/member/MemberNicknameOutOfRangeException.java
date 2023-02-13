package ProjectDoge.StudentSoup.exception.member;

public class MemberNicknameOutOfRangeException extends RuntimeException {
    public MemberNicknameOutOfRangeException() {
        super();
    }

    public MemberNicknameOutOfRangeException(String message) {
        super(message);
    }

    public MemberNicknameOutOfRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberNicknameOutOfRangeException(Throwable cause) {
        super(cause);
    }

    protected MemberNicknameOutOfRangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
