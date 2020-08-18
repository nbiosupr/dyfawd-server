package site.deepsleep.dyfawd.domain.collecteddata;

import lombok.Builder;
import lombok.Getter;
import site.deepsleep.dyfawd.domain.BaseTimeEntity;

import javax.persistence.Entity;

@Getter
@Builder
public class SensorDataForRank{
    private final String area1;
    private final String area2;
    private final Long cnt;

    public SensorDataForRank(String area1, String area2, Long cnt) {
        this.area1 = area1;
        this.area2 = area2;
        this.cnt = cnt;
    }

    @Override
    public String toString() {
        return "SensorDataForRank{" +
                "area1='" + area1 + '\'' +
                ", area2='" + area2 + '\'' +
                ", cnt=" + cnt +
                '}';
    }
}
