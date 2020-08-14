package site.deepsleep.dyfawd.service.rgc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import site.deepsleep.dyfawd.dto.rgc.RGCRequestDto;
import site.deepsleep.dyfawd.dto.rgc.RGCResponseDto;

@Service
public class RGCApiClient {

    @Value("${naver.client.id}")
    private String CLIENT_ID;

    @Value("${naver.client.secret}")
    private String CLIENT_SECRET;

    private String BASE_URL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?";

    private final RestTemplate restTemplate = new RestTemplate();
    
    // TODO: 오류 코드에 대한 예외처리
    public RGCResponseDto requestAddress(RGCRequestDto requestDto) {

        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", this.CLIENT_ID);
        headers.set("X-NCP-APIGW-API-KEY", this.CLIENT_SECRET);

        final HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = this.BASE_URL + requestDto.toQueryString();

        return restTemplate.exchange(url, HttpMethod.GET, entity, RGCResponseDto.class).getBody();
    }
}
