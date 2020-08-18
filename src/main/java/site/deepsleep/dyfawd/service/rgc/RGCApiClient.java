package site.deepsleep.dyfawd.service.rgc;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import site.deepsleep.dyfawd.advice.exception.rgc.CInvalidPositionException;
import site.deepsleep.dyfawd.advice.exception.rgc.CServerResponseNotOKException;
import site.deepsleep.dyfawd.web.dto.rgc.RGCRequestDto;
import site.deepsleep.dyfawd.web.dto.rgc.RGCResponseDto;

@RequiredArgsConstructor
@Service
public class RGCApiClient {

    @Value("${naver.client.id}")
    private String CLIENT_ID;

    @Value("${naver.client.secret}")
    private String CLIENT_SECRET;

    private final String BASE_URL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?";

    private final RestTemplate restTemplate;
    
    // TODO: 오류 코드에 대한 예외처리
    public RGCResponseDto requestAddress(RGCRequestDto requestDto) {

        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", this.CLIENT_ID);
        headers.set("X-NCP-APIGW-API-KEY", this.CLIENT_SECRET);

        final HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = this.BASE_URL + requestDto.toQueryString();

        ResponseEntity<RGCResponseDto> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, RGCResponseDto.class);

        RGCResponseDto responseDto = responseEntity.getBody();

        if (responseDto == null) {
            throw new NullPointerException("[RGC] responseDto has null!");
        }
        // 좌표에 대한 주소를 찾을 수 없는 경우 Exception 발생시킴
        else if (responseDto.getStatus().getCode() == 3) {
            throw new CInvalidPositionException("[RGC] Response code 3, no data which you request using coords!");
        } 
        // 좌표 값에 문제가 있는 경우
        else if (responseDto.getStatus().getCode() == 100) {
            throw new CInvalidPositionException("[RGC] Response code 100, coords param is not valid!");
        }
        // 응답이 200OK가 아닐시 Exception 발생시킴
        else if(responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new CServerResponseNotOKException(responseEntity.getStatusCode());
        }

        return responseDto;
    }
}
