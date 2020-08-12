package site.deepsleep.dyfawd.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.deepsleep.dyfawd.domain.collecteddata.CollectedData;

@Getter
@NoArgsConstructor
public class DataSaveRequestDto {
    private Float longitude;
    private Float latitude;

    @Builder
    public DataSaveRequestDto(Float longitude, Float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public CollectedData toEntity(){
        return CollectedData.builder()
                .latitude(this.latitude)
                .longitude(this.longitude)
                .build();
    }
}
