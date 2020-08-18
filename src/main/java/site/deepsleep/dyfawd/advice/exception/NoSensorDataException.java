package site.deepsleep.dyfawd.advice.exception;

public class NoSensorDataException extends RuntimeException {
    public NoSensorDataException() {
    }

    public NoSensorDataException(String message) {
        super(message);
    }

    public NoSensorDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSensorDataException(Throwable cause) {
        super(cause);
    }

    public NoSensorDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
