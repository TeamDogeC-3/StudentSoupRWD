package ProjectDoge.StudentSoup.exception.board;

public class BoardIdNotSentException extends RuntimeException{

    public  BoardIdNotSentException() {
        super();
    }

    public  BoardIdNotSentException(String message) {
        super(message);
    }

    public BoardIdNotSentException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardIdNotSentException(Throwable cause) {
        super(cause);
    }

    protected  BoardIdNotSentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
