package site.deepsleep.dyfawd.domain.collecteddata;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.deepsleep.dyfawd.domain.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class SensorDataGIS extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dataId;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false, length = 100)
    private String area1;

    @Column(nullable = false, length = 100)
    private String area2;

    @Column(nullable = false, length = 100)
    private String area3;

    @Builder
    public SensorDataGIS(Double longitude, Double latitude, String area1, String area2, String area3, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.area1 = area1;
        this.area2 = area2;
        this.area3 = area3;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
