package site.deepsleep.dyfawd.domain.collecteddata;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.deepsleep.dyfawd.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class SensorData extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dataId;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double latitude;

    @Builder
    public SensorData(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
