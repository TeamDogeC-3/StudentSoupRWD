package ProjectDoge.StudentSoup.exception.admin;

public class MemberClassificationNotAdminException extends RuntimeException {
    public MemberClassificationNotAdminException() {
        super();
    }

    public MemberClassificationNotAdminException(String message) {
        super(message);
    }

    public MemberClassificationNotAdminException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberClassificationNotAdminException(Throwable cause) {
        super(cause);
    }

    protected MemberClassificationNotAdminException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
