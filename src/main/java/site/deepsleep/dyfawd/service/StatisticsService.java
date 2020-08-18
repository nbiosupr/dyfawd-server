package site.deepsleep.dyfawd.service;

import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.deepsleep.dyfawd.advice.exception.NoLevelDataException;
import site.deepsleep.dyfawd.advice.exception.NoRankDataException;
import site.deepsleep.dyfawd.advice.exception.NoSensorDataException;
import site.deepsleep.dyfawd.domain.asleeplevel.AsleepLevel;
import site.deepsleep.dyfawd.domain.asleeplevel.AsleepLevelRepository;
import site.deepsleep.dyfawd.domain.asleeprank.AsleepRank;
import site.deepsleep.dyfawd.domain.asleeprank.AsleepRankRepository;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataGIS;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataGISRepository;
import site.deepsleep.dyfawd.web.dto.SensorDataDto;
import site.deepsleep.dyfawd.web.dto.statistics.LevelResponseDto;
import site.deepsleep.dyfawd.web.dto.statistics.RankResponseDto;
import site.deepsleep.dyfawd.web.dto.statistics.StatisticsRequestDto;
import site.deepsleep.dyfawd.web.dto.statistics.StatisticsResponseDto;

import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class StatisticsService {
    private final AsleepLevelRepository levelRepository;
    private final AsleepRankRepository rankRepository;
    private final SensorDataGISRepository sensorDataGISRepository;

    @Transactional(readOnly = true)
    public RankResponseDto getRankResult() throws Exception{
        try {
            AsleepRank rank = rankRepository.findTopByOrderByCreatedAtDesc().orElseThrow(NullPointerException::new);

            RankResponseDto rankResponseDto = new RankResponseDto(rank);

            return rankResponseDto;
        } catch (NullPointerException e) {
            throw new NoRankDataException("Rank Repository에 적정한 데이터가 없습니다.", e);
        }
    }

    public LevelResponseDto getLevelResult() throws Exception{
        try {
            AsleepLevel level = levelRepository.findTopByOrderByCreatedAtDesc().orElseThrow(NullPointerException::new);

            LevelResponseDto levelResponseDto = new LevelResponseDto(level);
            return levelResponseDto;
        } catch (NullPointerException e) {
            throw new NoLevelDataException("Level Repository에 적정한 데이터가 없습니다.", e);
        }
     }

    public StatisticsResponseDto getStatisticsResult(StatisticsRequestDto requestDto){
        int year = requestDto.getYear();
        boolean isMonthly = requestDto.getIsMonthly();
        boolean isNationwide = requestDto.getIsNationwide();

        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        List<SensorDataGIS> listOfSensorData = new ArrayList<>();
        String[] timeKeyArr = {"zeroToThree", "fourToSeven", "eightToEleven", "twelveToThirteen", "sixteenToNineteen", "twentyToTwentyThree"};

        Map<Integer, Map<String, List<SensorDataDto>>> resultMap = new HashMap<>();

        if (isMonthly) {
            startDateTime = LocalDateTime.of(year, 1, 1, 0 ,0, 0);
            endDateTime = LocalDateTime.of(year, 12, 31, 23, 59,59);

            for(int i =1 ; i<13; i++) {
                resultMap.put(i, new HashMap<>());
            }
        } else {
            int month = requestDto.getMonth();

            startDateTime = LocalDateTime.of(year, month, 1, 0, 0, 0);
            endDateTime = LocalDateTime.of(year, month, YearMonth.of(year, month).lengthOfMonth(), 23, 59 ,59);

            for(int i =1 ; i < YearMonth.of(year, month).lengthOfMonth()+1; i++) {
                resultMap.put(i, new HashMap<>());
            }
        }

        for(Integer key: resultMap.keySet()) {
            Map<String, List<SensorDataDto>> tempMap = resultMap.get(key);
            tempMap.put("zeroToThree", new ArrayList<>());
            tempMap.put("fourToSeven", new ArrayList<>());
            tempMap.put("eightToEleven", new ArrayList<>());
            tempMap.put("twelveToFifteen", new ArrayList<>());
            tempMap.put("sixteenToNineteen", new ArrayList<>());
            tempMap.put("twentyToTwentyThree", new ArrayList<>());
        }

        if(isNationwide) {
            //전국 처리
            listOfSensorData = sensorDataGISRepository.findByCreatedAtBetween(startDateTime, endDateTime);
        } else {
            // 지역별 처리
            listOfSensorData = sensorDataGISRepository.findByArea1AndArea2AndCreatedAtBetween(requestDto.getCity(), requestDto.getCountry(), startDateTime, endDateTime);
        }
        
        // 자료 없음 에러처리
        if (listOfSensorData.size() == 0) {
            throw new NoSensorDataException("해당되는 Sensor 데이터가 없습니다.");
        }

        // 시간 분류용
        List<Pair<LocalTime, LocalTime>> timePairList = new ArrayList<>();
        for (int i=0; i<6; i++) {
            int startTimeNum = 4*i;
            LocalTime startTime = LocalTime.of(startTimeNum, 0);
            LocalTime endTime = LocalTime.of(startTimeNum+3, 59);

            Pair<LocalTime, LocalTime> timePair = new Pair<>(startTime, endTime);
            timePairList.add(timePair);
        }

        for(SensorDataGIS data : listOfSensorData) {
            int key = 0;
            if(isMonthly) {
                key = data.getCreatedAt().getMonth().getValue();
            }else {
                key = data.getCreatedAt().getDayOfMonth();
            }
            Map<String, List<SensorDataDto>> tempMap = resultMap.get(key);

            for (int i = 0; i < 6; i++) {
                Pair<LocalTime, LocalTime> timePair = timePairList.get(i);
                String timeKey = timeKeyArr[i];

                if (timePair.getKey().getHour() <= data.getCreatedAt().getHour() && data.getCreatedAt().getHour() <= timePair.getValue().getHour()) {
                    tempMap.get(timeKey).add(new SensorDataDto(data));
                }
            }

        }

        return StatisticsResponseDto.builder().isMonthly(isMonthly).isNationwide(isNationwide).results(resultMap).build();
    }
}
