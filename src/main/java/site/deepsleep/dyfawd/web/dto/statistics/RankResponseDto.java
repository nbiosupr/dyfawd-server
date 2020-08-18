package site.deepsleep.dyfawd.web.dto.statistics;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.deepsleep.dyfawd.domain.asleeprank.AsleepRank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class RankResponseDto {
    private List<RankItem> ranks;
    private LocalDateTime createdAt;

    @Getter
    @NoArgsConstructor
    public static class RankItem{
        private String area;
        private Long sum;

        @Builder
        public RankItem(String area, Long sum) {
            this.area = area;
            this.sum = sum;
        }
    }

    public RankResponseDto(AsleepRank asleepRank) {
        this.createdAt = asleepRank.getCreatedAt();
        ranks = new ArrayList<>();

        ranks.add(RankItem.builder().area(asleepRank.getFirstArea()).sum(asleepRank.getFirstSum()).build());
        ranks.add(RankItem.builder().area(asleepRank.getSecondArea()).sum(asleepRank.getSecondSum()).build());
        ranks.add(RankItem.builder().area(asleepRank.getThirdArea()).sum(asleepRank.getThirdSum()).build());
        ranks.add(RankItem.builder().area(asleepRank.getFourthArea()).sum(asleepRank.getFourthSum()).build());
        ranks.add(RankItem.builder().area(asleepRank.getFifthArea()).sum(asleepRank.getFifthSum()).build());
    }
}
