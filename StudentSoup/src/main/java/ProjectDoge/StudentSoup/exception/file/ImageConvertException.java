package ProjectDoge.StudentSoup.exception.file;

public class ImageConvertException extends RuntimeException {
    public ImageConvertException() {
        super();
    }

    public ImageConvertException(String message) {
        super(message);
    }

    public ImageConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageConvertException(Throwable cause) {
        super(cause);
    }

    protected ImageConvertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
