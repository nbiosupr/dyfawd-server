package site.deepsleep.dyfawd.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.deepsleep.dyfawd.advice.exception.NoAvailableAreaException;
import site.deepsleep.dyfawd.advice.exception.NoLevelDataException;
import site.deepsleep.dyfawd.advice.exception.NoRankDataException;
import site.deepsleep.dyfawd.advice.exception.NoSensorDataException;
import site.deepsleep.dyfawd.advice.exception.rgc.CInvalidPositionException;
import site.deepsleep.dyfawd.advice.exception.security.CSensorAuthFailedException;
import site.deepsleep.dyfawd.advice.exception.security.CSensorTokenAuthFailedException;
import site.deepsleep.dyfawd.service.ResponseService;
import site.deepsleep.dyfawd.web.dto.response.CommonResult;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

    //@ExceptionHandler(Exception.class)
    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(-9999, "알수 없는 오류 입니다." );
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    protected CommonResult unsupportedMediaType(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(-3, "application/json으로 media type을 설정해주세요");
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult invalidSensorDataException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(-1, "요청 JSON 형태에 맞춰서 다시 전송해주세요." );
    }

    @ExceptionHandler(NoLevelDataException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResult noLevelDataException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(-2, "아직 레벨 데이터가 없습니다.");
    }

    @ExceptionHandler(NoRankDataException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResult noRankDataException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(-2, "아직 랭크 데이터가 없습니다.");
    }

    @ExceptionHandler(NoSensorDataException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResult noSensorDataException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(-2, "해당되는 센서 데이터가 없습니다.");
    }

    @ExceptionHandler(CInvalidPositionException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResult cInvalidPositionException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(-2, "GPS 좌표가 올바르지 않습니다.");
    }

    @ExceptionHandler(NoAvailableAreaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResult noAvailableAreaException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(-2, "조회 가능한 도시 목록이 없습니다.");
    }

    @ExceptionHandler(CSensorAuthFailedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected CommonResult cSensorAuthFailedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(-4, "sensor id나 pw가 잘못되었습니다.");
    }

    @ExceptionHandler(CSensorTokenAuthFailedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected CommonResult cSensorTokenAuthFailedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(-5, "토근이 잘못되었습니다.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected CommonResult accessDeniedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(-6, "권한이 없습니다.");
    }
}
