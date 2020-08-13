package site.deepsleep.dyfawd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.deepsleep.dyfawd.dto.rgc.RGCRequestDto;
import site.deepsleep.dyfawd.dto.rgc.RGCResponseDto;
import site.deepsleep.dyfawd.service.rgc.RGCApiClient;

@RequiredArgsConstructor
@Service
public class RGCService {

    private final RGCApiClient apiClient;

    @Transactional(readOnly = true)
    public RGCResponseDto findByPos(double longitude, double latitude) {
        RGCRequestDto requestDto = new RGCRequestDto(longitude, latitude);

        return apiClient.requestAddress(requestDto);
    }

}
