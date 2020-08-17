package site.deepsleep.dyfawd.domain.collecteddata;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SensorDataGISRepository extends JpaRepository<SensorDataGIS, Long> {
    @Query(value = "select new site.deepsleep.dyfawd.domain.collecteddata.SensorDataForRank(d.area1 as area1, d.area2 as area2, count(d) as cnt1) from SensorDataGIS d group by d.area1, d.area2 order by cnt1 desc")
    List<SensorDataForRank> getDataForRank(Pageable pageable);
}
