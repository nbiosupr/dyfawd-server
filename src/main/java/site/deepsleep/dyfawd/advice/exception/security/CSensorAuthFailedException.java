package site.deepsleep.dyfawd.advice.exception.security;

public class CSensorAuthFailedException extends RuntimeException {
    public CSensorAuthFailedException() {
    }

    public CSensorAuthFailedException(String message) {
        super(message);
    }

    public CSensorAuthFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CSensorAuthFailedException(Throwable cause) {
        super(cause);
    }

    public CSensorAuthFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
