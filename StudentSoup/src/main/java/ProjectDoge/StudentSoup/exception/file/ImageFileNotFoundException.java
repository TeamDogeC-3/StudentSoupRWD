package ProjectDoge.StudentSoup.exception.file;

public class ImageFileNotFoundException extends RuntimeException{
    public ImageFileNotFoundException() {
        super();
    }

    public ImageFileNotFoundException(String message) {
        super(message);
    }

    public ImageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageFileNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ImageFileNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
