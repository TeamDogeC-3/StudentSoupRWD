package ProjectDoge.StudentSoup.exception.page;

public class PagingLimitEqualsZeroException extends RuntimeException {
    public PagingLimitEqualsZeroException() {
        super();
    }

    public PagingLimitEqualsZeroException(String message) {
        super(message);
    }

    public PagingLimitEqualsZeroException(String message, Throwable cause) {
        super(message, cause);
    }

    public PagingLimitEqualsZeroException(Throwable cause) {
        super(cause);
    }

    protected PagingLimitEqualsZeroException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
