package site.deepsleep.dyfawd.web.dto.statistics;

import lombok.Data;
import site.deepsleep.dyfawd.web.dto.SensorDataDto;

import java.util.List;

@Data
public class StatisticsResponseDto {
    private boolean isNationwide;
    private boolean isMonthly;

    public static class Times {
        private int month;
        private int day;
        private List<SensorDataDto> zeroToThree;
        private List<SensorDataDto> fourToSeven;
        private List<SensorDataDto> eightToEleven;
        private List<SensorDataDto> twelveToFifteen;
        private List<SensorDataDto> sixteenToNineteen;
        private List<SensorDataDto> twentyToTwentyThree;
    }

}
