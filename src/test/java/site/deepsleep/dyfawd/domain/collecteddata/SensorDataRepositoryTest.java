package site.deepsleep.dyfawd.domain.collecteddata;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SensorDataRepositoryTest extends TestCase {

    @Autowired
    SensorDataRepository sensorDataRepository;

    @After
    public void cleanup() {
        sensorDataRepository.deleteAll();
    }

    @Test
    public void 수집데이터_등록_테스트(){
        //given
        Double longitude = 37.51815414152548;
        Double latitude = 126.92278699567869;

        SensorData data = SensorData.builder().longitude(longitude).latitude(latitude).build();

        //when
        sensorDataRepository.save(data);

        //then
        SensorData gottenData = sensorDataRepository.findAll().get(0);
        assertThat(gottenData.getLatitude()).isEqualTo(latitude);
        assertThat(gottenData.getLongitude()).isEqualTo(longitude);
    }
}