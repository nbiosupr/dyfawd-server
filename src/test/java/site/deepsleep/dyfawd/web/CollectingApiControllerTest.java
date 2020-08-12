package site.deepsleep.dyfawd.web;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplateExtensionsKt;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import site.deepsleep.dyfawd.domain.collecteddata.CollectedData;
import site.deepsleep.dyfawd.domain.collecteddata.CollectedDataRepository;
import site.deepsleep.dyfawd.web.dto.DataSaveRequestDto;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CollectingApiControllerTest extends TestCase {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CollectedDataRepository collectedDataRepository;

    @After
    public void tearDown() throws Exception {
        collectedDataRepository.deleteAll();
    }

    @Test
    public void 수집데이터_등록_REST() throws Exception {
        //given
        Float longitude = 37.51815414152548F;
        Float latitude = 126.92278699567869F;

        String url = "http://localhost:"+ port +"/api/v1/data";
        DataSaveRequestDto requestDto = DataSaveRequestDto.builder().latitude(latitude).longitude(longitude).build();

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        CollectedData gottenData = collectedDataRepository.findAll().get(0);
        assertThat(gottenData.getLatitude()).isEqualTo(latitude);
        assertThat(gottenData.getLongitude()).isEqualTo(longitude);
    }
}