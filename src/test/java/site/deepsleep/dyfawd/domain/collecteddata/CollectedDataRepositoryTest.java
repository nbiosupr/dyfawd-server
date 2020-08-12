package site.deepsleep.dyfawd.domain.collecteddata;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectedDataRepositoryTest extends TestCase {

    @Autowired
    CollectedDataRepository collectedDataRepository;

    @After
    public void cleanup() {
        collectedDataRepository.deleteAll();
    }

    @Test
    public void 수집데이터_등록_테스트(){
        //given
        Float longitude = 37.51815414152548F;
        Float latitude = 126.92278699567869F;

        CollectedData data = CollectedData.builder().longitude(longitude).latitude(latitude).build();

        //when
        collectedDataRepository.save(data);

        //then
        CollectedData gottenData = collectedDataRepository.findAll().get(0);
        assertThat(gottenData.getLatitude()).isEqualTo(latitude);
        assertThat(gottenData.getLongitude()).isEqualTo(longitude);
    }
}