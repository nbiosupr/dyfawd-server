package site.deepsleep.dyfawd.config.commandline;

import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import site.deepsleep.dyfawd.domain.asleeplevel.AsleepLevel;
import site.deepsleep.dyfawd.domain.asleeplevel.AsleepLevelRepository;
import site.deepsleep.dyfawd.domain.asleeprank.AsleepRank;
import site.deepsleep.dyfawd.domain.asleeprank.AsleepRankRepository;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataForRank;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataGIS;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataGISRepository;
import site.deepsleep.dyfawd.domain.sensor.SensorInfo;
import site.deepsleep.dyfawd.domain.sensor.SensorInfoRepository;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Slf4j
@Profile({"test", "local"})
@RequiredArgsConstructor
@Configuration
public class DummyDataConfiguration implements CommandLineRunner {

    private final SensorDataGISRepository sensorDataGISRepository;
    private final AsleepRankRepository asleepRankRepository;
    private final AsleepLevelRepository asleepLevelRepository;
    private final SensorInfoRepository sensorInfoRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        this.loadDummySensorData();
        this.makeRankData();
        this.makeLevelDummyData();
        this.makeDummySensorId();
    }

    private void makeRankData() {
        log.debug("make rank dummy data start!");

        List<SensorDataForRank> results =  sensorDataGISRepository.getDataForRank(PageRequest.of(0,5));

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

        asleepRankRepository.save(asleepRank);

        log.debug("make rank dummy data finish!");
    }

    private void loadDummySensorData() throws Exception{
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMddHH");
        int count = 0;
        boolean firstFlag = true;

        log.debug("working dir: " + System.getProperty("user.dir"));
        log.debug("CSV reading start!");
        CSVReader reader = new CSVReader(new FileReader("./dummy_export.csv"));
        String [] nextLine;

        while ((nextLine = reader.readNext()) != null) {
            if(firstFlag) {
                firstFlag = false;
                continue;
            }
            LocalDateTime dateTime = LocalDateTime.parse(nextLine[0], f);
            double longitude = Double.parseDouble(nextLine[1]);
            double latitude = Double.parseDouble(nextLine[2]);
            String area1 = nextLine[3];
            String area2 = nextLine[4];
            String area3 = nextLine[5];

            SensorDataGIS dataGIS = SensorDataGIS.builder()
                    .createdAt(dateTime)
                    .updatedAt(dateTime)
                    .longitude(longitude)
                    .latitude(latitude)
                    .area1(area1)
                    .area2(area2)
                    .area3(area3)
                    .build();

            sensorDataGISRepository.save(dataGIS);
            count++;
        }

        log.debug("CSV reading finish! whole item count: " + count);
    }

    private void makeLevelDummyData() {
        log.debug("AsleepLevel Dummy Data insert start!");

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

        asleepLevelRepository.save(asleepLevel1);

        log.debug("AsleepLevel Dummy Data insert finish!");
    }
    
    //TODO: 지우기
    private void makeDummySensorId() {
        String id = "deep_sleep";
        String pw = "security203!";

        sensorInfoRepository.save(SensorInfo.builder()
                .sensorId(id)
                .password(passwordEncoder.encode(pw))
                .roles(Collections.singletonList("ROLE_SENSOR"))
                .build());

    }
}
