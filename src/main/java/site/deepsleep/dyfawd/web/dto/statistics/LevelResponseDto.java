package site.deepsleep.dyfawd.web.dto.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import site.deepsleep.dyfawd.domain.asleeplevel.AsleepLevel;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class LevelResponseDto {
    private int seoul;
    private int incheon;
    private int chuncheon;
    private int gangleung;
    private int andong;
    private int ulsan;
    private int daegu;
    private int busan;
    private int changwon;
    private int gwangju;
    private int jeonju;
    private int daejeon;
    private int cheongju;
    private int suwon;
    private LocalDateTime createdAt;

    public LevelResponseDto(AsleepLevel asleepLevel) {
        this.seoul = asleepLevel.getSeoul();
        this.incheon = asleepLevel.getIncheon();
        this.chuncheon = asleepLevel.getChuncheon();
        this.gangleung = asleepLevel.getGangleung();
        this.andong = asleepLevel.getAndong();
        this.ulsan = asleepLevel.getUlsan();
        this.daegu = asleepLevel.getDaegu();
        this.busan = asleepLevel.getBusan();
        this.changwon = asleepLevel.getChangwon();
        this.gwangju = asleepLevel.getGwangju();
        this.jeonju = asleepLevel.getJeonju();
        this.daejeon = asleepLevel.getDaejeon();
        this.cheongju = asleepLevel.getCheongju();
        this.suwon = asleepLevel.getSuwon();

        this.createdAt = asleepLevel.getCreatedAt();
    }
}
