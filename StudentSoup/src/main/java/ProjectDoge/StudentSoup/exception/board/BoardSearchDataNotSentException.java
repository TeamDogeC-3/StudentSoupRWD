package ProjectDoge.StudentSoup.exception.board;

public class BoardSearchDataNotSentException extends RuntimeException{

    public BoardSearchDataNotSentException() {
        super();
    }

    public BoardSearchDataNotSentException(String message) {
        super(message);
    }

    public BoardSearchDataNotSentException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardSearchDataNotSentException(Throwable cause) {
        super(cause);
    }

    protected BoardSearchDataNotSentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
