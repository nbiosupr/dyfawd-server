package site.deepsleep.dyfawd.advice.exception.rgc;

public class CInvalidPositionException extends RuntimeException {
    public CInvalidPositionException() {
    }

    public CInvalidPositionException(String message) {
        super(message);
    }

    public CInvalidPositionException(String message, Throwable cause) {
        super(message, cause);
    }
}
