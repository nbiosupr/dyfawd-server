package site.deepsleep.dyfawd.domain.sensor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorInfoRepository extends JpaRepository<SensorInfo, Long> {
    Optional<SensorInfo> findBySensorId(String sensorId);
}
