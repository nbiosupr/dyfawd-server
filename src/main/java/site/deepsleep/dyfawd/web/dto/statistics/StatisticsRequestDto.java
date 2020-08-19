package site.deepsleep.dyfawd.web.dto.statistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatisticsRequestDto {
    @ApiModelProperty(value="전국/지역별 여부", required = true)
    private Boolean isNationwide;
    
    @ApiModelProperty(value="월별/일별 여부", required = true)
    private Boolean isMonthly;

    @ApiModelProperty(value="연도", required = true)
    private int year;

    @ApiModelProperty(value="월(월별 조회시 비워도 됨)")
    private int month;

    @ApiModelProperty(value="시/도(전국 조회시 비워도 됨)")
    private String city;

    @ApiModelProperty(value="군/구/시(전국 조회시 비워도 됨)")
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
