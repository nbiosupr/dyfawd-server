package site.deepsleep.dyfawd.domain.asleeplevel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.deepsleep.dyfawd.domain.BaseTimeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class AsleepLevel extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Builder
    public AsleepLevel(int seoul,
                       int incheon,
                       int chuncheon,
                       int gangleung,
                       int andong,
                       int ulsan,
                       int daegu,
                       int busan,
                       int changwon,
                       int gwangju,
                       int jeonju,
                       int daejeon,
                       int cheongju,
                       int suwon
                       ) {
        this.seoul = seoul;
        this.incheon = incheon;
        this.chuncheon = chuncheon;
        this.gangleung = gangleung;
        this.andong = andong;
        this.ulsan = ulsan;
        this.daegu = daegu;
        this.busan = busan;
        this.changwon = changwon;
        this.gwangju = gwangju;
        this.jeonju = jeonju;
        this.daejeon = daejeon;
        this.cheongju = cheongju;
        this.suwon = suwon;
    }
}
