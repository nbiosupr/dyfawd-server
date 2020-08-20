package site.deepsleep.dyfawd.web.controller.openapi;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.deepsleep.dyfawd.advice.exception.NoAvailableAreaException;
import site.deepsleep.dyfawd.service.ResponseService;
import site.deepsleep.dyfawd.service.StatisticsService;
import site.deepsleep.dyfawd.web.dto.response.ListResult;
import site.deepsleep.dyfawd.web.dto.response.SingleResult;
import site.deepsleep.dyfawd.web.dto.statistics.*;

import java.util.List;

@Api(tags = {"Statistics"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
public class StatisticsApiController {

    private final StatisticsService statisticsService;
    private final ResponseService responseService;

    @ApiOperation(value=" 졸음 누적 순위 조회", notes = "졸음 누적 지역 순위를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회성공"),
            @ApiResponse(code = 404, message = "데이터가 없습니다."),
    })
    @GetMapping("/ranks")
    public SingleResult<RankResponseDto> getRanks() throws Exception {
        RankResponseDto responseDto = statisticsService.getRankResult();

        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value="졸음 레벨 조회", notes = "각 지역의 졸음 레벨을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회성공"),
            @ApiResponse(code = 404, message = "데이터가 없습니다."),
    })
    @GetMapping("/levels")
    public SingleResult<LevelResponseDto> getLevels() throws Exception {
        LevelResponseDto responseDto = statisticsService.getLevelResult();

        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value="졸음 통계 데이터 조회(시간별)", notes = "지역별/전국별, 월별/일별 졸음 통계 데이터를 얻을 수 있다.(시간별 분류)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회성공"),
            @ApiResponse(code = 404, message = "데이터가 없습니다."),
    })
    @GetMapping("/data")
    public SingleResult<StatisticsDistributedResponseDto> getData(StatisticsRequestDto requestDto) throws Exception{
        // 중앙 에러 처리. 매핑 에러는 filter 에서 미리 처리
        boolean errorFlag = false;
        if (!requestDto.getIsMonthly() && (1 > requestDto.getMonth() || requestDto.getMonth() > 12)) errorFlag = true;
        else if (!requestDto.getIsNationwide() && (requestDto.getCity() == null || requestDto.getCountry() == null)) errorFlag = true;
        if(errorFlag) throw new IllegalArgumentException("요청 인수가 잘못되었습니다");

        StatisticsDistributedResponseDto responseDto = statisticsService.getStatisticsResultDistributedByTime(requestDto);

        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value="졸음 통계 데이터 조회(원블럭)", notes = "지역별/전국별, 월별/일별 졸음 통계 데이터를 얻을 수 있다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회성공"),
            @ApiResponse(code = 404, message = "데이터가 없습니다."),
    })
    @GetMapping("/one_block_data")
    public SingleResult<StatisticsLinearResponseDto> getOneBlockData(StatisticsRequestDto requestDto) throws Exception{
        // 중앙 에러 처리. 매핑 에러는 filter 에서 미리 처리
        boolean errorFlag = false;
        if (!requestDto.getIsMonthly() && (1 > requestDto.getMonth() || requestDto.getMonth() > 12)) errorFlag = true;
        else if (!requestDto.getIsNationwide() && (requestDto.getCity() == null || requestDto.getCountry() == null)) errorFlag = true;
        if(errorFlag) throw new IllegalArgumentException("요청 인수가 잘못되었습니다");

        StatisticsLinearResponseDto responseDto = statisticsService.getStatisticsResult(requestDto);

        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value="요청가능한 도/시 목록 조회", notes = "현재 요청가능한 도/시 목록을 조회할 수 있음")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회성공"),
            @ApiResponse(code = 404, message = "데이터가 없습니다."),
    })
    @GetMapping("/areas")
    public ListResult<ResponseAreaDto> getAvailableArea() {
        List<ResponseAreaDto> availableAreaList = statisticsService.getAvailableAreaList();
        
        // 조회가능한 도시가 없는 경우 예외처리
        if(availableAreaList.size() == 0) {
            throw new NoAvailableAreaException();
        }

        return responseService.getListResult(availableAreaList);
    }
}