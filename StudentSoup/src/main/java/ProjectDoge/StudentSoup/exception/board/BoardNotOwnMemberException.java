package ProjectDoge.StudentSoup.exception.board;

public class BoardNotOwnMemberException extends RuntimeException {

    public BoardNotOwnMemberException() {
        super();
    }

    public BoardNotOwnMemberException(String message) {
        super(message);
    }

    public BoardNotOwnMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardNotOwnMemberException(Throwable cause) {
        super(cause);
    }

    protected BoardNotOwnMemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
