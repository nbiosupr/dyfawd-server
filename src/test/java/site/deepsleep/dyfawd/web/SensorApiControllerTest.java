package site.deepsleep.dyfawd.web;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataGIS;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataGISRepository;
import site.deepsleep.dyfawd.web.dto.SensorDataSaveRequestDto;
import site.deepsleep.dyfawd.web.dto.response.CommonResult;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SensorApiControllerTest extends TestCase {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SensorDataGISRepository sensorDataGISRepository;

    //given
    private final Double longitude = 128.12345;
    private final Double latitude = 37.98776;


    @After
    public void tearDown(){
        sensorDataGISRepository.deleteAll();
    }

    @Test
    public void 수집데이터_등록_REST_GIS(){
        //given
        String url = "http://localhost:"+ port +"/api/v1/sensordata";
        SensorDataSaveRequestDto requestDto = SensorDataSaveRequestDto.builder().latitude(latitude).longitude(longitude).build();

        //when
        ResponseEntity<CommonResult> responseEntity = restTemplate.postForEntity(url, requestDto, CommonResult.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        SensorDataGIS gottenData = sensorDataGISRepository.findAll().get(0);
        assertThat(gottenData.getLatitude()).isEqualTo(latitude);
        assertThat(gottenData.getLongitude()).isEqualTo(longitude);
        assertThat(gottenData.getArea2()).isEqualTo("인제군");
    }
}