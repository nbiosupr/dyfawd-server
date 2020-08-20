package site.deepsleep.dyfawd.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.deepsleep.dyfawd.advice.exception.NoLevelDataException;
import site.deepsleep.dyfawd.advice.exception.NoRankDataException;
import site.deepsleep.dyfawd.domain.asleeplevel.AsleepLevel;
import site.deepsleep.dyfawd.domain.asleeplevel.AsleepLevelRepository;
import site.deepsleep.dyfawd.domain.asleeprank.AsleepRank;
import site.deepsleep.dyfawd.domain.asleeprank.AsleepRankRepository;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataGISRepository;
import site.deepsleep.dyfawd.web.dto.SensorDataDto;
import site.deepsleep.dyfawd.web.dto.statistics.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class StatisticsService {
    private final AsleepLevelRepository levelRepository;
    private final AsleepRankRepository rankRepository;
    private final SensorDataService sensorDataService;

    @Transactional(readOnly = true)
    public RankResponseDto getRankResult(){
        try {
            AsleepRank rank = rankRepository.findTopByOrderByCreatedAtDesc().orElseThrow(NullPointerException::new);

            RankResponseDto rankResponseDto = new RankResponseDto(rank);

            return rankResponseDto;
        } catch (NullPointerException e) {
            throw new NoRankDataException("Rank Repository에 적정한 데이터가 없습니다.", e);
        }
    }

    @Transactional(readOnly = true)
    public LevelResponseDto getLevelResult(){
        try {
            AsleepLevel level = levelRepository.findTopByOrderByCreatedAtDesc().orElseThrow(NullPointerException::new);

            LevelResponseDto levelResponseDto = new LevelResponseDto(level);
            return levelResponseDto;
        } catch (NullPointerException e) {
            throw new NoLevelDataException("Level Repository에 적정한 데이터가 없습니다.", e);
        }
     }

    @Transactional(readOnly = true)
    public StatisticsDistributedResponseDto getStatisticsResultDistributedByTime(StatisticsRequestDto requestDto){
        int year = requestDto.getYear();
        int month = requestDto.getMonth();
        boolean isMonthly = requestDto.getIsMonthly();
        boolean isNationwide = requestDto.getIsNationwide();
        Map<Integer, Map<String, List<SensorDataDto>>> resultMap = null;

        if(isNationwide) {
            if(isMonthly) {
                resultMap = sensorDataService.getDistributedByTimeMapNationwideMonthly(year);
            } else {
                resultMap = sensorDataService.getDistributedByTimeMapNationwideDaily(year, month);
            }
        } else {
            if(isMonthly) {
                resultMap = sensorDataService.getDistributedByTimeMapLocalMonthly(year, requestDto.getCity(), requestDto.getCountry());
            } else {
                resultMap = sensorDataService.getDistributedByTimeMapLocalDaily(year, month, requestDto.getCity(), requestDto.getCountry());
            }
        }

        return StatisticsDistributedResponseDto.builder().isMonthly(isMonthly).isNationwide(isNationwide).results(resultMap).build();
    }

    @Transactional(readOnly = true)
    public StatisticsLinearResponseDto getStatisticsResult(StatisticsRequestDto requestDto){
        int year = requestDto.getYear();
        int month = requestDto.getMonth();
        boolean isMonthly = requestDto.getIsMonthly();
        boolean isNationwide = requestDto.getIsNationwide();

        List<SensorDataDto> dtoList = null;

        if(isNationwide) {
            if(isMonthly) {
                dtoList = sensorDataService.getDtoListByYear(year);
            } else {
                dtoList = sensorDataService.getDtoListByYearMonth(year, month);
            }
        } else {
            if(isMonthly) {
                dtoList = sensorDataService.getDtoListByYearCityCountry(year, requestDto.getCity(), requestDto.getCountry());
            } else {
                dtoList = sensorDataService.getDtoListByYearMonthCityCountry(year, month, requestDto.getCity(), requestDto.getCountry());
            }
        }

        return StatisticsLinearResponseDto.builder().isMonthly(isMonthly).isNationwide(isNationwide).results(dtoList).build();
    }
}
