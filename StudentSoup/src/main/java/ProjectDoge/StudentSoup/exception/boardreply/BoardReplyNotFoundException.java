package ProjectDoge.StudentSoup.exception.boardreply;

public class BoardReplyNotFoundException extends RuntimeException{

    public BoardReplyNotFoundException() {
        super();
    }
    public BoardReplyNotFoundException(String message) {
        super(message);
    }

    public BoardReplyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardReplyNotFoundException(Throwable cause) {
        super(cause);
    }

    protected BoardReplyNotFoundException(String message, Throwable cause,
                                          boolean enableSuppression,
                                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
