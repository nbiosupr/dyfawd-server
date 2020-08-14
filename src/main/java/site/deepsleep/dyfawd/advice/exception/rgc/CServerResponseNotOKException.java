package site.deepsleep.dyfawd.advice.exception.rgc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.nio.charset.Charset;

public class CServerResponseNotOKException extends HttpStatusCodeException {
    public CServerResponseNotOKException(HttpStatus statusCode) {
        super(statusCode);
    }

    public CServerResponseNotOKException(HttpStatus statusCode, String statusText) {
        super(statusCode, statusText);
    }

    public CServerResponseNotOKException(HttpStatus statusCode, String statusText, byte[] responseBody, Charset responseCharset) {
        super(statusCode, statusText, responseBody, responseCharset);
    }

    public CServerResponseNotOKException(HttpStatus statusCode, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(statusCode, statusText, responseHeaders, responseBody, responseCharset);
    }
}
