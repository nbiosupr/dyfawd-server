package site.deepsleep.dyfawd.service.rgc;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import site.deepsleep.dyfawd.dto.rgc.RGCRequestDto;
import site.deepsleep.dyfawd.dto.rgc.RGCResponseDto;

@RequiredArgsConstructor
@Service
public class RGCApiClient {

    @Value("${naver.client.id}")
    private final String CLIENT_ID;

    @Value("${naver.client.secret}")
    private final String CLIENT_SECRET;

    private final String BASE_URL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?";

    private final RestTemplate restTemplate;

    // TODO 테스트해보기
    public RGCResponseDto requestAddress(RGCRequestDto requestDto) {

        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", this.CLIENT_ID);
        headers.set("X-NCP-APIGW-API-KEY", this.CLIENT_SECRET);

        final HttpEntity<String> entity = new HttpEntity<>(headers);

        StringBuffer sb = new StringBuffer();
        String url = sb.append(this.BASE_URL).append(requestDto.toQueryString()).toString();

        return restTemplate.exchange(url, HttpMethod.GET, entity, RGCResponseDto.class).getBody();
    }
}
