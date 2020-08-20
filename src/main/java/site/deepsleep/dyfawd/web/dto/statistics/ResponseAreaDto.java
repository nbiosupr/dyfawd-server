package site.deepsleep.dyfawd.web.dto.statistics;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ResponseAreaDto {
    String city;
    List<String> countryList;

    @Builder
    public ResponseAreaDto(String city, List<String> countryList) {
        this.city = city;
        this.countryList = countryList;
    }
}
