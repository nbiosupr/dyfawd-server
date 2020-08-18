package site.deepsleep.dyfawd.advice.exception;

public class NoRankDataException extends RuntimeException {
    public NoRankDataException() {
    }

    public NoRankDataException(String message) {
        super(message);
    }

    public NoRankDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRankDataException(Throwable cause) {
        super(cause);
    }

    public NoRankDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
