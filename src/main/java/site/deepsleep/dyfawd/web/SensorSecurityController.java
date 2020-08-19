package site.deepsleep.dyfawd.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.deepsleep.dyfawd.advice.exception.security.CSensorAuthFailedException;
import site.deepsleep.dyfawd.config.security.SensorJwtTokenProvider;
import site.deepsleep.dyfawd.domain.sensor.SensorInfo;
import site.deepsleep.dyfawd.domain.sensor.SensorInfoRepository;
import site.deepsleep.dyfawd.service.ResponseService;
import site.deepsleep.dyfawd.web.dto.TokenRequestDto;
import site.deepsleep.dyfawd.web.dto.response.SingleResult;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/security")
public class SensorSecurityController {

    private final SensorInfoRepository sensorInfoRepository;
    private final SensorJwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final ResponseService responseService;

    @PostMapping(value="token")
    public SingleResult<String> getToken(@RequestBody TokenRequestDto requestDto) throws Exception {
        SensorInfo sensorInfo = sensorInfoRepository
                .findBySensorId(requestDto.getId())
                .orElseThrow(CSensorAuthFailedException::new);

        if (!passwordEncoder.matches(requestDto.getPw(), sensorInfo.getPassword()))
            throw new CSensorAuthFailedException();

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(sensorInfo.getSensorInfoId()), sensorInfo.getRoles()));
    }

}
