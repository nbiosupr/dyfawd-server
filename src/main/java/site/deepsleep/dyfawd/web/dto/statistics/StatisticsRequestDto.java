package site.deepsleep.dyfawd.web.dto.statistics;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatisticsRequestDto {
    private boolean isNationwide;
    private boolean isMonthly;
    private int year;
    private int month;
    private String city;
    private String country;

    @Builder
    public StatisticsRequestDto(boolean isNationwide, boolean isMonthly, int year, int month, String city, String country) {
        this.isNationwide = isNationwide;
        this.isMonthly = isMonthly;
        this.year = year;
        this.month = month;
        this.city = city;
        this.country = country;
    }
}
