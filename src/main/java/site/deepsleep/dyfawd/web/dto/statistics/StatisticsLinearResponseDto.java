package site.deepsleep.dyfawd.web.dto.statistics;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.deepsleep.dyfawd.web.dto.SensorDataDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class StatisticsLinearResponseDto {
    private Boolean isNationwide;
    private Boolean isMonthly;
    private List<SensorDataDto> results;

    @Builder
    public StatisticsLinearResponseDto(Boolean isNationwide, Boolean isMonthly, List<SensorDataDto> results) {
        this.isNationwide = isNationwide;
        this.isMonthly = isMonthly;
        this.results = results;
    }
}
