package site.deepsleep.dyfawd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.deepsleep.dyfawd.advice.exception.NoSensorDataException;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataGIS;
import site.deepsleep.dyfawd.domain.collecteddata.SensorDataGISRepository;
import site.deepsleep.dyfawd.web.dto.SensorDataDto;
import site.deepsleep.dyfawd.web.dto.rgc.RGCResponseDto;
import site.deepsleep.dyfawd.service.rgc.RGCService;
import site.deepsleep.dyfawd.web.dto.SensorDataSaveRequestDto;
import site.deepsleep.dyfawd.web.dto.response.CommonResult;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class SensorDataService {

    private final SensorDataGISRepository sensorDataGISRepository;
    private final ResponseService responseService;
    private final RGCService rgcService;

    private static String[] timeKeyArr = {"zeroToThree", "fourToSeven", "eightToEleven", "twelveToFifteen", "sixteenToNineteen", "twentyToTwentyThree"};


    @Transactional
    public CommonResult save(SensorDataSaveRequestDto requestDto) {
        // 소숫점 6자리 아래 반올림
        double longitude = Math.round(requestDto.getLongitude() * 1000000) / 1000000.0;
        double latitude = Math.round(requestDto.getLatitude() * 1000000) / 1000000.0;
        RGCResponseDto rgcResponseDto = rgcService.findByPos(longitude, latitude);

        String area1 = rgcResponseDto.getResults().get(0).getRegion().getArea1().getName();
        String area2 = rgcResponseDto.getResults().get(0).getRegion().getArea2().getName();
        String area3 = rgcResponseDto.getResults().get(0).getRegion().getArea3().getName();

        sensorDataGISRepository.save(requestDto.toEntity(area1, area2, area3));

        return responseService.getSuccessResult();
    }

    // 시간 분류 메소드 모음
    @Transactional(readOnly = true)
    public Map<Integer, Map<String, List<SensorDataDto>>> getDistributedByTimeMapNationwideDaily (int year, int month) {
        List<SensorDataGIS> listOfSensorData = this.getDataBetweenMonth(year, month);

        if (listOfSensorData.size() == 0) {
            throw new NoSensorDataException("해당되는 Sensor 데이터가 없습니다.");
        }

        return makeFinalResultMap(this.initDailyResultMap(year, month), listOfSensorData, false);
    }

    @Transactional(readOnly = true)
    public Map<Integer, Map<String, List<SensorDataDto>>> getDistributedByTimeMapNationwideMonthly (int year) {
        List<SensorDataGIS> listOfSensorData = this.getDataBetweenYear(year);

        if (listOfSensorData.size() == 0) {
            throw new NoSensorDataException("해당되는 Sensor 데이터가 없습니다.");
        }

        return makeFinalResultMap(this.initMonthlyResultMap(year), listOfSensorData, true);
    }

    @Transactional(readOnly = true)
    public Map<Integer, Map<String, List<SensorDataDto>>> getDistributedByTimeMapLocalDaily (int year, int month, String city, String country) {
        List<SensorDataGIS> listOfSensorData = this.getDataByAreaBetweenMonth(year, month, city, country);

        if (listOfSensorData.size() == 0) {
            throw new NoSensorDataException("해당되는 Sensor 데이터가 없습니다.");
        }

        return makeFinalResultMap(this.initDailyResultMap(year, month), listOfSensorData, false);
    }

    @Transactional(readOnly = true)
    public Map<Integer, Map<String, List<SensorDataDto>>> getDistributedByTimeMapLocalMonthly (int year, String city, String country) {
        List<SensorDataGIS> listOfSensorData = this.getDataByAreaBetweenYear(year, city, country);

        if (listOfSensorData.size() == 0) {
            throw new NoSensorDataException("해당되는 Sensor 데이터가 없습니다.");
        }

        return makeFinalResultMap(this.initMonthlyResultMap(year), listOfSensorData, true);
    }

    // 시간 미분류 메소드 모음
    @Transactional(readOnly = true)
    public List<SensorDataDto> getDtoListByYear(int year) {
        List<SensorDataDto> resultList = this.makeSensorDtoListFromEntity(this.getDataBetweenYear(year));

        if(resultList.size() == 0) {
            throw new NoSensorDataException("센서 데이터가 없습니다.");
        }

        return resultList;
    }

    @Transactional(readOnly = true)
    public List<SensorDataDto> getDtoListByYearMonth(int year, int month) {
        List<SensorDataDto> resultList = this.makeSensorDtoListFromEntity(this.getDataBetweenMonth(year, month));

        if(resultList.size() == 0) {
            throw new NoSensorDataException("센서 데이터가 없습니다.");
        }

        return resultList;
    }

    @Transactional(readOnly = true)
    public List<SensorDataDto> getDtoListByYearCityCountry(int year, String city, String country) {
        List<SensorDataDto> resultList = this.makeSensorDtoListFromEntity(this.getDataByAreaBetweenYear(year, city, country));

        if(resultList.size() == 0) {
            throw new NoSensorDataException("센서 데이터가 없습니다.");
        }

        return resultList;
    }

    @Transactional(readOnly = true)
    public List<SensorDataDto> getDtoListByYearMonthCityCountry(int year, int month, String city, String country) {
        List<SensorDataDto> resultList = this.makeSensorDtoListFromEntity(this.getDataByAreaBetweenMonth(year, month, city, country));

        if(resultList.size() == 0) {
            throw new NoSensorDataException("센서 데이터가 없습니다.");
        }

        return resultList;
    }

    private Map<Integer, Map<String, List<SensorDataDto>>> initMonthlyResultMap(int year) {
        Map<Integer, Map<String, List<SensorDataDto>>> resultMap = new HashMap<>();

        for(int i =1 ; i<13; i++) {
            resultMap.put(i, new HashMap<>());
        }

        return resultMap;
    }

    private Map<Integer, Map<String, List<SensorDataDto>>> initDailyResultMap(int year, int month) {
        Map<Integer, Map<String, List<SensorDataDto>>> resultMap = new HashMap<>();

        for(int i =1 ; i < YearMonth.of(year, month).lengthOfMonth()+1; i++) {
            resultMap.put(i, new HashMap<>());
        }

        return resultMap;
    }

    private Map<Integer, Map<String, List<SensorDataDto>>> makeFinalResultMap(
            Map<Integer, Map<String, List<SensorDataDto>>> resultMap
            ,List<SensorDataGIS> listOfSensorData
            , boolean isMonthly) {

        for(Integer key: resultMap.keySet()) {
            Map<String, List<SensorDataDto>> tempMap = resultMap.get(key);
            for(String timeKey: timeKeyArr) {
                tempMap.put(timeKey, new ArrayList<>());
            }
        }

        for(SensorDataGIS data : listOfSensorData) {
            int key = 0;
            if(isMonthly) {
                key = data.getCreatedAt().getMonth().getValue();
            }else {
                key = data.getCreatedAt().getDayOfMonth();
            }
            Map<String, List<SensorDataDto>> tempMap = resultMap.get(key);

            List<Pair<LocalTime, LocalTime>> timePairList = this.makeTimePairList();

            for (int i = 0; i < 6; i++) {
                Pair<LocalTime, LocalTime> timePair = timePairList.get(i);
                String timeKey = timeKeyArr[i];

                if (timePair.getKey().getHour() <= data.getCreatedAt().getHour() && data.getCreatedAt().getHour() <= timePair.getValue().getHour()) {
                    tempMap.get(timeKey).add(new SensorDataDto(data));
                }
            }

        }

        return resultMap;
    }

    // 시간 간격 나누기용 타임페어 리스트 만들기
    private List<Pair<LocalTime, LocalTime>> makeTimePairList(){
        List<Pair<LocalTime, LocalTime>> timePairList = new ArrayList<>();
        for (int i=0; i<6; i++) {
            int startTimeNum = 4*i;
            LocalTime startTime = LocalTime.of(startTimeNum, 0);
            LocalTime endTime = LocalTime.of(startTimeNum+3, 59);

            Pair<LocalTime, LocalTime> timePair = new ImmutablePair<>(startTime, endTime);
            timePairList.add(timePair);
        }

        return timePairList;
    }

    private List<SensorDataDto> makeSensorDtoListFromEntity(List<SensorDataGIS> listOfSensorData) {
        List<SensorDataDto> resultList = new ArrayList<>();

        for(SensorDataGIS data : listOfSensorData) {
            resultList.add(new SensorDataDto(data));
        }

        return resultList;
    }

    // 지역별 연도 데이터 가져오기
    @Transactional(readOnly = true)
    public List<SensorDataGIS> getDataByAreaBetweenYear(int year, String city, String country) {
        LocalDateTime startDateTime = LocalDateTime.of(year, 1, 1, 0 ,0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(year, 12, 31, 23, 59,59);

        return sensorDataGISRepository.findByArea1AndArea2AndCreatedAtBetween(city, country, startDateTime, endDateTime);
    }

    // 지역별 한달 데이터 가져오기
    @Transactional(readOnly = true)
    public List<SensorDataGIS> getDataByAreaBetweenMonth(int year, int month, String city, String country) {
        LocalDateTime startDateTime = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(year, month, YearMonth.of(year, month).lengthOfMonth(), 23, 59 ,59);

        return sensorDataGISRepository.findByArea1AndArea2AndCreatedAtBetween(city, country, startDateTime, endDateTime);
    }

    // 전국 연도 데이터 가져오기
    @Transactional(readOnly = true)
    public List<SensorDataGIS> getDataBetweenYear(int year) {
        LocalDateTime startDateTime = LocalDateTime.of(year, 1, 1, 0 ,0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(year, 12, 31, 23, 59,59);

        return sensorDataGISRepository.findByCreatedAtBetween(startDateTime, endDateTime);
    }

    // 전국 한달 데이터 가져오기
    @Transactional(readOnly = true)
    public List<SensorDataGIS> getDataBetweenMonth(int year, int month) {
        LocalDateTime startDateTime = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(year, month, YearMonth.of(year, month).lengthOfMonth(), 23, 59 ,59);

        return sensorDataGISRepository.findByCreatedAtBetween(startDateTime, endDateTime);
    }
}
