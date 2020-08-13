package site.deepsleep.dyfawd.advice.exception;

public class CInvalidSensorDataException extends RuntimeException {
    public CInvalidSensorDataException() {
        super();
    }

    public CInvalidSensorDataException(String message) {
        super(message);
    }

    public CInvalidSensorDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
