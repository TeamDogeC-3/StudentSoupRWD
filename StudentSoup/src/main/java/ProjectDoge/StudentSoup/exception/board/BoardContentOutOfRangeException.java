package ProjectDoge.StudentSoup.exception.board;

public class BoardContentOutOfRangeException extends RuntimeException {
    public BoardContentOutOfRangeException() {
        super();
    }

    public BoardContentOutOfRangeException(String message) {
        super(message);
    }

    public BoardContentOutOfRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardContentOutOfRangeException(Throwable cause) {
        super(cause);
    }

    protected BoardContentOutOfRangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
