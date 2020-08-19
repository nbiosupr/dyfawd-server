package site.deepsleep.dyfawd.domain.asleeplevel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
public class AsleepLevelRepositoryTest{

    @Autowired
    private AsleepLevelRepository asleepLevelRepository;

    @Test
    public void 테스트_레벨_저장후_최근조회() {
        //given
        AsleepLevel asleepLevel1 = AsleepLevel.builder()
                .andong(1)
                .busan(2)
                .changwon(1)
                .cheongju(1)
                .daegu(3)
                .gangleung(2)
                .chuncheon(1)
                .daejeon(1)
                .gwangju(1)
                .incheon(2)
                .jeonju(1)
                .seoul(3)
                .suwon(2)
                .ulsan(3)
                .build();

        AsleepLevel asleepLevel2 = AsleepLevel.builder()
                .andong(2)
                .busan(2)
                .changwon(1)
                .cheongju(1)
                .daegu(3)
                .gangleung(2)
                .chuncheon(1)
                .daejeon(1)
                .gwangju(1)
                .incheon(2)
                .jeonju(1)
                .seoul(3)
                .suwon(2)
                .ulsan(3)
                .build();

        // when
        asleepLevelRepository.save(asleepLevel1);
        asleepLevelRepository.save(asleepLevel2);

        AsleepLevel asleepLevel = asleepLevelRepository.findTopByOrderByCreatedAtDesc().orElseThrow(NullPointerException::new);

        // then
        List<AsleepLevel> results = asleepLevelRepository.findAll();

        assertThat(results.size()).isEqualTo(2);
        assertThat(asleepLevel.getAndong()).isEqualTo(2); // 마지막으로 추가된 데이터의 안동레벨을 2로 설정하였음.
    }
}