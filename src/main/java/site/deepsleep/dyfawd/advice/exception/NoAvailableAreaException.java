package site.deepsleep.dyfawd.advice.exception;

public class NoAvailableAreaException extends RuntimeException {
    public NoAvailableAreaException() {
    }

    public NoAvailableAreaException(String message) {
        super(message);
    }

    public NoAvailableAreaException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAvailableAreaException(Throwable cause) {
        super(cause);
    }

    public NoAvailableAreaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
