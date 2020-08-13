package site.deepsleep.dyfawd.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.deepsleep.dyfawd.service.ResponseService;
import site.deepsleep.dyfawd.web.dto.response.CommonResult;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

    //@ExceptionHandler(Exception.class)
    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(-9999, "알수 없는 오류 입니다." );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult invalidSensorDataException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(-1, "요청 JSON 형태에 맞춰서 다시 전송해주세요." );
    }
}
