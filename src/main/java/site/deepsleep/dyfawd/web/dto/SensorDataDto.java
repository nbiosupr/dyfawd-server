package site.deepsleep.dyfawd.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataGIS;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class SensorDataDto {
    private Double longitude;
    private Double latitude;
    private String city;
    private String country;
    private LocalDateTime creationDate;

    public SensorDataDto(SensorDataGIS sensorDataGIS) {
        this.longitude = sensorDataGIS.getLongitude();
        this.latitude = sensorDataGIS.getLatitude();
        this.city = sensorDataGIS.getArea1();
        this.country = sensorDataGIS.getArea2();
        this.creationDate = sensorDataGIS.getCreatedAt();
    }
}
