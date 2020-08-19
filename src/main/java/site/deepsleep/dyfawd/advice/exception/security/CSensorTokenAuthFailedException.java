package site.deepsleep.dyfawd.advice.exception.security;

public class CSensorTokenAuthFailedException extends RuntimeException{
    public CSensorTokenAuthFailedException() {
    }

    public CSensorTokenAuthFailedException(String message) {
        super(message);
    }

    public CSensorTokenAuthFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CSensorTokenAuthFailedException(Throwable cause) {
        super(cause);
    }

    public CSensorTokenAuthFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
