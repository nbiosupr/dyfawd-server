package site.deepsleep.dyfawd.web.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class SensorDataDto {
    private double longitude;
    private double latitude;
    private String area1;
    private String area2;
    private LocalDateTime creationDate;
}
