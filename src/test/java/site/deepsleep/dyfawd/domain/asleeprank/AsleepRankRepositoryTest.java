package site.deepsleep.dyfawd.domain.asleeprank;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsleepRankRepositoryTest {

    @Autowired
    AsleepRankRepository repo;

    @Test
    public void testFindTopByOrderByCreatedAtDesc() {
        //when
        AsleepRank asleepRank = repo.findTopByOrderByCreatedAtDesc().orElseThrow(NullPointerException::new);

        //then
        assertThat(asleepRank.getFirstArea()).isEqualTo("경기도 평택시");
        assertThat(asleepRank.getFirstSum()).isEqualTo(25L);
    }
}