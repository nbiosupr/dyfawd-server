package site.deepsleep.dyfawd.domain.collecteddata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class SensorDataGISRepositoryTest{

    @Autowired
    SensorDataGISRepository dataRepo;

    @Test
    public void 테스트_구단위와_시간간격_find_all() {
        // given
        String area1 = "제주특별자치도";
        String area2 = "제주시";
        LocalDateTime startDateTime = LocalDateTime.of(2020, 1, 1, 0 ,0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 12, 31, 23, 59,59);

        // when
        List<SensorDataGIS> results = dataRepo.findByArea1AndArea2AndCreatedAtBetween(area1, area2, startDateTime, endDateTime);

        // then
        assertThat(results.size()).isEqualTo(25);
    }
}