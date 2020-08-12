package site.deepsleep.dyfawd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.deepsleep.dyfawd.domain.collecteddata.CollectedData;
import site.deepsleep.dyfawd.domain.collecteddata.CollectedDataRepository;
import site.deepsleep.dyfawd.web.dto.DataSaveRequestDto;

@RequiredArgsConstructor
@Service
public class CollectingService {
    private final CollectedDataRepository collectedDataRepository;

    public Long save(DataSaveRequestDto requestDto) {
        return collectedDataRepository.save(requestDto.toEntity()).getDataId();
    }
}
