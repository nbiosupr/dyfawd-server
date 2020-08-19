package site.deepsleep.dyfawd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataGISRepository;
import site.deepsleep.dyfawd.web.dto.rgc.RGCResponseDto;
import site.deepsleep.dyfawd.service.rgc.RGCService;
import site.deepsleep.dyfawd.web.dto.SensorDataSaveRequestDto;
import site.deepsleep.dyfawd.web.dto.response.CommonResult;

@RequiredArgsConstructor
@Service
public class SensorDataService {

    private final SensorDataGISRepository sensorDataGISRepository;
    private final ResponseService responseService;
    private final RGCService rgcService;

    @Transactional
    public CommonResult save(SensorDataSaveRequestDto requestDto) {
        // 소숫점 6자리 아래 반올림
        double longitude = Math.round(requestDto.getLongitude() * 1000000) / 1000000.0;
        double latitude = Math.round(requestDto.getLatitude() * 1000000) / 1000000.0;
        RGCResponseDto rgcResponseDto = rgcService.findByPos(longitude, latitude);

        String area1 = rgcResponseDto.getResults().get(0).getRegion().getArea1().getName();
        String area2 = rgcResponseDto.getResults().get(0).getRegion().getArea2().getName();
        String area3 = rgcResponseDto.getResults().get(0).getRegion().getArea3().getName();

        sensorDataGISRepository.save(requestDto.toEntity(area1, area2, area3));

        return responseService.getSuccessResult();
    }
}
