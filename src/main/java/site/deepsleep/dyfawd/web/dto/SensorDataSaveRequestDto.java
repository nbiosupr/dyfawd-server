package site.deepsleep.dyfawd.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataGIS;

@Getter
@Setter
@NoArgsConstructor
public class SensorDataSaveRequestDto {
    private Double longitude;
    private Double latitude;

    @Builder
    public SensorDataSaveRequestDto(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public SensorDataGIS toEntity(String area1, String area2, String area3) {
        return SensorDataGIS.builder()
                .latitude(this.latitude)
                .longitude(this.longitude)
                .area1(area1)
                .area2(area2)
                .area3(area3).build();
    }
}
