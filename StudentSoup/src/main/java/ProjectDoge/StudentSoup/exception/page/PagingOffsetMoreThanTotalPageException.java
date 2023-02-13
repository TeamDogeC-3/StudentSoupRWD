package ProjectDoge.StudentSoup.exception.page;

public class PagingOffsetMoreThanTotalPageException extends RuntimeException {
    public PagingOffsetMoreThanTotalPageException() {
        super();
    }

    public PagingOffsetMoreThanTotalPageException(String message) {
        super(message);
    }

    public PagingOffsetMoreThanTotalPageException(String message, Throwable cause) {
        super(message, cause);
    }

    public PagingOffsetMoreThanTotalPageException(Throwable cause) {
        super(cause);
    }

    protected PagingOffsetMoreThanTotalPageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
