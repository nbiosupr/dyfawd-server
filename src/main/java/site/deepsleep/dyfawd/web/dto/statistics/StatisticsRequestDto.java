package site.deepsleep.dyfawd.web.dto.statistics;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatisticsRequestDto {
    private Boolean isNationwide;
    private Boolean isMonthly;
    private int year;
    private int month;
    private String city;
    private String country;

    @Builder
    public StatisticsRequestDto(Boolean isNationwide, Boolean isMonthly, int year, int month, String city, String country) {
        this.isNationwide = isNationwide;
        this.isMonthly = isMonthly;
        this.year = year;
        this.month = month;
        this.city = city;
        this.country = country;
    }

    public String toPathString() {
        return "?isNationwide=" + isNationwide +
                "&isMonthly=" + isMonthly +
                "&year=" + year +
                "&month=" + month +
                "&city=" + city +
                "&country=" + country;
    }

    @Override
    public String toString() {
        return "StatisticsRequestDto{" +
                "isNationwide=" + isNationwide +
                ", isMonthly=" + isMonthly +
                ", year=" + year +
                ", month=" + month +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
