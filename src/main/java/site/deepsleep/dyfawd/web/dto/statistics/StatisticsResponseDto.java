package site.deepsleep.dyfawd.web.dto.statistics;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataGIS;
import site.deepsleep.dyfawd.web.dto.SensorDataDto;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class StatisticsResponseDto {
    private Boolean isNationwide;
    private Boolean isMonthly;
    private Map<Integer, Map<String, List<SensorDataDto>>> results;

    @Builder
    public StatisticsResponseDto(Boolean isNationwide, Boolean isMonthly, Map<Integer, Map<String, List<SensorDataDto>>> results) {
        this.isNationwide = isNationwide;
        this.isMonthly = isMonthly;
        this.results = results;
    }
}
