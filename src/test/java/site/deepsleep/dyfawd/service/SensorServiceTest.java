package site.deepsleep.dyfawd.service;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import site.deepsleep.dyfawd.domain.collecteddata.SensorData;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataRepository;
import site.deepsleep.dyfawd.web.dto.SensorDataSaveRequestDto;
import site.deepsleep.dyfawd.web.dto.response.CommonResult;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SensorServiceTest extends TestCase {

    @Autowired
    SensorDataRepository sensorDataRepository;

    @Autowired
    SensorService sensorService;

    @After
    public void tearDown() {
        sensorDataRepository.deleteAll();
    }

    //@Test
    public void 서비스_삽입_테스트() {
        //given
        Double longitude = 37.51815414152548;
        Double latitude = 126.92278699567869;

        SensorDataSaveRequestDto requestDto = SensorDataSaveRequestDto.builder().latitude(latitude).longitude(longitude).build();

        //when
        CommonResult result = sensorService.save(requestDto);

        //then
        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(CommonResult.class);

        SensorData gottenData = sensorDataRepository.findAll().get(0);
        assertThat(gottenData.getLatitude()).isEqualTo(latitude);
        assertThat(gottenData.getLongitude()).isEqualTo(longitude);
    }


}