package site.deepsleep.dyfawd.domain.asleeprank;

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
public class AsleepRank extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstArea;
    private Long firstSum;
    private String secondArea;
    private Long secondSum;
    private String thirdArea;
    private Long thirdSum;
    private String fourthArea;
    private Long fourthSum;
    private String fifthArea;
    private Long fifthSum;

    @Builder
    public AsleepRank(String firstArea,
                      Long firstSum,
                      String secondArea,
                      Long secondSum,
                      String thirdArea,
                      Long thirdSum,
                      String fourthArea,
                      Long fourthSum,
                      String fifthArea,
                      Long fifthSum) {
        this.firstArea = firstArea;
        this.firstSum = firstSum;
        this.secondArea = secondArea;
        this.secondSum = secondSum;
        this.thirdArea = thirdArea;
        this.thirdSum = thirdSum;
        this.fourthArea = fourthArea;
        this.fourthSum = fourthSum;
        this.fifthArea = fifthArea;
        this.fifthSum = fifthSum;
    }
}
