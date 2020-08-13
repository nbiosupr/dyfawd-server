package site.deepsleep.dyfawd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataRepository;
import site.deepsleep.dyfawd.web.dto.SensorDataSaveRequestDto;
import site.deepsleep.dyfawd.web.dto.response.CommonResult;

@RequiredArgsConstructor
@Service
public class SensorService {

    private final SensorDataRepository sensorDataRepository;
    private final ResponseService responseService;

    public CommonResult save(SensorDataSaveRequestDto requestDto) {
        sensorDataRepository.save(requestDto.toEntity());
        return responseService.getSuccessResult();
    }
}
