package site.deepsleep.dyfawd.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.deepsleep.dyfawd.service.ResponseService;
import site.deepsleep.dyfawd.service.StatisticsService;
import site.deepsleep.dyfawd.web.dto.response.SingleResult;
import site.deepsleep.dyfawd.web.dto.statistics.LevelResponseDto;
import site.deepsleep.dyfawd.web.dto.statistics.RankResponseDto;
import site.deepsleep.dyfawd.web.dto.statistics.StatisticsRequestDto;
import site.deepsleep.dyfawd.web.dto.statistics.StatisticsResponseDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
public class StatisticsApiController {

    private final StatisticsService statisticsService;
    private final ResponseService responseService;

    @GetMapping("/ranks")
    public SingleResult<RankResponseDto> getRanks() throws Exception {
        RankResponseDto responseDto = statisticsService.getRankResult();

        return responseService.getSingleResult(responseDto);
    }

    @GetMapping("/levels")
    public SingleResult<LevelResponseDto> getLevels() throws Exception {
        LevelResponseDto responseDto = statisticsService.getLevelResult();

        return responseService.getSingleResult(responseDto);
    }

    @GetMapping("/data")
    public SingleResult<StatisticsResponseDto> getData(StatisticsRequestDto requestDto) throws Exception{
        // 중앙 에러 처리. 매핑 에러는 filter 에서 미리 처리
        boolean errorFlag = false;
        if (!requestDto.getIsMonthly() && (1 > requestDto.getMonth() || requestDto.getMonth() > 12)) errorFlag = true;
        else if (!requestDto.getIsNationwide() && (requestDto.getCity() == null || requestDto.getCountry() == null)) errorFlag = true;
        if(errorFlag) throw new IllegalArgumentException("요청 인수가 잘못되었습니다");

        StatisticsResponseDto responseDto = statisticsService.getStatisticsResult(requestDto);

        return responseService.getSingleResult(responseDto);
    }
}