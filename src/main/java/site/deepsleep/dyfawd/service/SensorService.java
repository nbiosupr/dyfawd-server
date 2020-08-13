package site.deepsleep.dyfawd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataRepository;
import site.deepsleep.dyfawd.web.dto.SensorDataSaveRequestDto;

@RequiredArgsConstructor
@Service
public class SensorService {

    private final SensorDataRepository sensorDataRepository;

    public Long save(SensorDataSaveRequestDto requestDto) {
        return sensorDataRepository.save(requestDto.toEntity()).getDataId();
    }
}
