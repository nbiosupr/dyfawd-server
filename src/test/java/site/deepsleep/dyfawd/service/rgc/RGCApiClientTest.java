package site.deepsleep.dyfawd.service.rgc;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import site.deepsleep.dyfawd.advice.exception.rgc.CInvalidPositionException;
import site.deepsleep.dyfawd.web.dto.rgc.RGCRequestDto;
import site.deepsleep.dyfawd.web.dto.rgc.RGCResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RGCApiClientTest extends TestCase {

    @Autowired
    private RGCApiClient apiClient;

    @Value("${naver.client.id}")
    private String CLIENT_ID;

    @Value("${naver.client.secret}")
    private String CLIENT_SECRET;


    @Test
    public void RGC_API_요청_응답_테스트() {
        //given
        float longitude = 128.12345f;
        float latitude = 37.98776f;

        RGCRequestDto requestDto = new RGCRequestDto(longitude, latitude);

        //when
        RGCResponseDto rgcResponseDto = apiClient.requestAddress(requestDto);

        //then
        assertThat(rgcResponseDto).isNotNull();
        assertThat(rgcResponseDto.getResults().get(0).getRegion().getArea0().getName()).isEqualTo("kr");
        assertThat(rgcResponseDto.getResults().get(0).getRegion().getArea2().getName()).isEqualTo("인제군");
    }

    @Test
    public void RGC_API_범위이상_테스트() {
        //given
        float longitude = 1f;
        float latitude = 1f;

        RGCRequestDto requestDto = new RGCRequestDto(longitude, latitude);
        CInvalidPositionException pe = null;

        //when
        try {
            RGCResponseDto rgcResponseDto = apiClient.requestAddress(requestDto);
        } catch (CInvalidPositionException e) {
            pe = e;
        }

        //then
        assertThat(pe).isNotNull();
        assertThat(pe).isInstanceOf(CInvalidPositionException.class);
    }

    //@Test
    public void api_로우_테스트() {
        //given
        //RGCRequestDto requestDto = new RGCRequestDto(longitude, latitude);
        String pos = "128.12345,37.98776";
        RGCRequestDto requestDto = RGCRequestDto.builder().coords(pos).output("json").build();

        RestTemplate restTemplate = new RestTemplate();
        String BASE_URL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?";

        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", this.CLIENT_ID);
        headers.set("X-NCP-APIGW-API-KEY", this.CLIENT_SECRET);

        final HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = BASE_URL + requestDto.toQueryString();
        System.out.println(url); // TODO: 지우기

        String string1 = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();

        assertThat(string1).isNotNull();
        System.out.println(string1);
    }

}