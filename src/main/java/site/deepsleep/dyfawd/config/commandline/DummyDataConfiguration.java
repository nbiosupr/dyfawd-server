package site.deepsleep.dyfawd.config.commandline;

import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataGIS;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataGISRepository;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DummyDataConfiguration implements CommandLineRunner {

    private final SensorDataGISRepository sensorDataGISRepository;

    @Override
    public void run(String... args) throws Exception {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMddHH");
        int count = 0;
        boolean firstFlag = true;

        logger.info("working dir: " + System.getProperty("user.dir"));
        logger.info("CSV reading start!");
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

        logger.info("CSV reading finish! whole item count: " + count);
    }
}
