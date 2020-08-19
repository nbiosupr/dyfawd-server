package site.deepsleep.dyfawd.web.controller.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.deepsleep.dyfawd.advice.exception.security.CSensorTokenAuthFailedException;
import site.deepsleep.dyfawd.web.dto.response.CommonResult;

import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@RestController
@RequestMapping(value= "/exception")
public class ExceptionController {

    @GetMapping(value="/entrypoint")
    public CommonResult entryPointException() {
        throw new CSensorTokenAuthFailedException();
    }

    @GetMapping(value="/accessdenied")
    public CommonResult accessdeneiedException() throws AccessDeniedException {
        throw new AccessDeniedException("");
    }
}
