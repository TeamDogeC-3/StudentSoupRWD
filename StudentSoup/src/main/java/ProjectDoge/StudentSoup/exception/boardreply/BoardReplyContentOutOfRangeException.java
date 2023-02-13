package ProjectDoge.StudentSoup.exception.boardreply;

public class BoardReplyContentOutOfRangeException extends RuntimeException {
    public BoardReplyContentOutOfRangeException() {
        super();
    }

    public BoardReplyContentOutOfRangeException(String message) {
        super(message);
    }

    public BoardReplyContentOutOfRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardReplyContentOutOfRangeException(Throwable cause) {
        super(cause);
    }

    protected BoardReplyContentOutOfRangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
