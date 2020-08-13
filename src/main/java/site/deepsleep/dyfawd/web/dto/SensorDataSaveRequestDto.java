package site.deepsleep.dyfawd.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.deepsleep.dyfawd.domain.collecteddata.SensorData;

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

    public SensorData toEntity(){
        return SensorData.builder()
                .latitude(this.latitude)
                .longitude(this.longitude)
                .build();
    }
}
