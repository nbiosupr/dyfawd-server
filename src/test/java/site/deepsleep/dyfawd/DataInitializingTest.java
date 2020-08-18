package site.deepsleep.dyfawd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import site.deepsleep.dyfawd.domain.asleeprank.AsleepRank;
import site.deepsleep.dyfawd.domain.asleeprank.AsleepRankRepository;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataForRank;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataGISRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataInitializingTest {

    @Autowired
    private SensorDataGISRepository repo;

    @Autowired
    private AsleepRankRepository asleepRepo;

    @Test
    public void 랭크데이터_초기화_테스트(){
         List<SensorDataForRank> results =  repo.getDataForRank(PageRequest.of(0,5));
         
        for (SensorDataForRank item : results) {
            System.out.println(item.toString());
        }

        AsleepRank asleepRank = AsleepRank.builder()
                .firstArea(results.get(0).getArea1() + " " + results.get(0).getArea2())
                .firstSum(results.get(0).getCnt())
                .secondArea(results.get(1).getArea1() + " " + results.get(1).getArea2())
                .secondSum(results.get(1).getCnt())
                .thirdArea(results.get(2).getArea1() + " " + results.get(2).getArea2())
                .thirdSum(results.get(2).getCnt())
                .fourthArea(results.get(3).getArea1() + " " + results.get(3).getArea2())
                .fourthSum(results.get(3).getCnt())
                .fifthArea(results.get(4).getArea1() + " " + results.get(4).getArea2())
                .fifthSum(results.get(4).getCnt())
                .build();

        asleepRepo.save(asleepRank);
    }
}
