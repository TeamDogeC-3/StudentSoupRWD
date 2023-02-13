package ProjectDoge.StudentSoup.exception.board;

public class BoardTitleOutOfRangeException extends RuntimeException {
    public BoardTitleOutOfRangeException() {
        super();
    }

    public BoardTitleOutOfRangeException(String message) {
        super(message);
    }

    public BoardTitleOutOfRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardTitleOutOfRangeException(Throwable cause) {
        super(cause);
    }

    protected BoardTitleOutOfRangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
