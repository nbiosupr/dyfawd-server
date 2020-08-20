package site.deepsleep.dyfawd.domain.collecteddata;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SensorDataGISRepository extends JpaRepository<SensorDataGIS, Long> {
    final String query1 = "select new site.deepsleep.dyfawd.domain.collecteddata.SensorDataForRank(d.area1 as area1, d.area2 as area2, count(d) as cnt1) from SensorDataGIS d group by d.area1, d.area2 order by cnt1 desc";

    @Query(value = query1)
    List<SensorDataForRank> getDataForRank(Pageable pageable);

    // 시간 간격으로 결과값 구하기
    List<SensorDataGIS> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<SensorDataGIS> findByArea1AndArea2AndCreatedAtBetween(String area1, String area2, LocalDateTime start, LocalDateTime end);

    Optional<SensorDataGIS> findTopByOrderByDataIdDesc();

    @Query(value="select d.area1 from SensorDataGIS d group by d.area1")
    List<String> findAllCityGroupByCity();

    @Query(value="select d.area2 from SensorDataGIS d where d.area1=:city group by d.area2")
    List<String> findAllCountryGroupByCountry(@Param("city") String city);
}
