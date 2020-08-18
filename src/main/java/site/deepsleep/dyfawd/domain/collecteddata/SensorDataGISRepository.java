package site.deepsleep.dyfawd.domain.collecteddata;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SensorDataGISRepository extends JpaRepository<SensorDataGIS, Long> {
    @Query(value = "select new site.deepsleep.dyfawd.domain.collecteddata.SensorDataForRank(d.area1 as area1, d.area2 as area2, count(d) as cnt1) from SensorDataGIS d group by d.area1, d.area2 order by cnt1 desc")
    List<SensorDataForRank> getDataForRank(Pageable pageable);

    // 시간 간격으로 결과값 구하기
    List<SensorDataGIS> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<SensorDataGIS> findByArea1AndArea2AndCreatedAtBetween(String area1, String area2, LocalDateTime start, LocalDateTime end);

    List<SensorDataGIS> findByArea1ContainsAndArea2ContainsAndCreatedAtBetween(String area1, String area2, LocalDateTime start, LocalDateTime end);
}
