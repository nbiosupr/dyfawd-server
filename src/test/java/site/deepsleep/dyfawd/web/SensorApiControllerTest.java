package site.deepsleep.dyfawd.web;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataGIS;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataGISRepository;
import site.deepsleep.dyfawd.domain.sensor.SensorInfo;
import site.deepsleep.dyfawd.domain.sensor.SensorInfoRepository;
import site.deepsleep.dyfawd.web.dto.SensorDataSaveRequestDto;
import site.deepsleep.dyfawd.web.dto.TokenRequestDto;
import site.deepsleep.dyfawd.web.dto.response.CommonResult;
import site.deepsleep.dyfawd.web.dto.response.SingleResult;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SensorApiControllerTest extends TestCase {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SensorDataGISRepository sensorDataGISRepository;

    @Autowired
    private SensorInfoRepository sensorInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void test_ok() {
        assertThat("ok").isEqualTo("ok");
    }

    //@Test
    public void 수집데이터_등록_REST_GIS(){
        //given
        String token = this.getDummyToken();

        String url = "http://localhost:"+ port +"/api/v1/sensor/data";
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-AUTH_TOKEN", token);

        //given
        Double longitude = 128.12345;
        Double latitude = 37.98776;
        SensorDataSaveRequestDto requestDto = SensorDataSaveRequestDto.builder().latitude(latitude).longitude(longitude).build();

        HttpEntity<SensorDataSaveRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        //when
        ResponseEntity<CommonResult> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                CommonResult.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);

        SensorDataGIS gottenData = sensorDataGISRepository.findTopByOrderByDataIdDesc().orElseThrow(NullPointerException::new);
        assertThat(gottenData.getArea2()).isEqualTo("제천시");

        System.out.println(gottenData.getCreatedAt());
    }

    private String getDummyToken(){
        //given
        String id = "dummy_id";
        String pw = "1234pw";

        makeDummySensorId(id, pw);
        String url = "http://localhost:"+ port +"/api/v1/security/token";
        TokenRequestDto requestDto = TokenRequestDto.builder().id(id).pw(pw).build();

        HttpEntity<TokenRequestDto> httpEntity = new HttpEntity<>(requestDto);

        ResponseEntity<SingleResult<String>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<SingleResult<String>>() {});

        System.out.println(responseEntity.getBody().getData());

        return responseEntity.getBody().getData();
    }

    private void makeDummySensorId(String id, String pw) {
        sensorInfoRepository.save(SensorInfo.builder()
                .sensorId(id)
                .password(passwordEncoder.encode(pw))
                .roles(Collections.singletonList("ROLE_SENSOR"))
                .build());
    }
}