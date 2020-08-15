package site.deepsleep.dyfawd.web.dto.statistics;

import lombok.Data;

import java.util.List;

@Data
public class RankDto {
    private List<RankItem> ranks;

    public static class RankItem{
        private String area1;
        private String area2;
        private int sum;
    }
}
