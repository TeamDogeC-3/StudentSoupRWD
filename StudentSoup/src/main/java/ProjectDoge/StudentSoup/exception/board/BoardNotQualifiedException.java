package ProjectDoge.StudentSoup.exception.board;

public class BoardNotQualifiedException extends RuntimeException{

    public BoardNotQualifiedException() {
        super();
    }

    public BoardNotQualifiedException(String message) {
        super(message);
    }

    public BoardNotQualifiedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardNotQualifiedException(Throwable cause) {
        super(cause);
    }

    protected BoardNotQualifiedException(String message, Throwable cause,
                                         boolean enableSuppression,
                                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
