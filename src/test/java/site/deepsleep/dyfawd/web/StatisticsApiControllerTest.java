package site.deepsleep.dyfawd.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import site.deepsleep.dyfawd.domain.asleeplevel.AsleepLevelRepository;
import site.deepsleep.dyfawd.domain.asleeprank.AsleepRankRepository;
import site.deepsleep.dyfawd.web.dto.response.SingleResult;
import site.deepsleep.dyfawd.web.dto.statistics.RankResponseDto;
import site.deepsleep.dyfawd.web.dto.statistics.StatisticsLinearResponseDto;
import site.deepsleep.dyfawd.web.dto.statistics.StatisticsRequestDto;
import site.deepsleep.dyfawd.web.dto.statistics.StatisticsDistributedResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StatisticsApiControllerTest{

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    AsleepRankRepository rankRepository;

    @Autowired
    AsleepLevelRepository levelRepository;
    
    @Test
    public void 테스트_랭크요청_api() {
        // given
        String url = "http://localhost:"+ port +"/api/v1/statistics/ranks";

        // when
        ResponseEntity<SingleResult<RankResponseDto>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null ,
                new ParameterizedTypeReference<SingleResult<RankResponseDto>>() {});

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        RankResponseDto rankResponseDto = responseEntity.getBody().getData();

        assertThat(rankResponseDto.getRanks().size()).isEqualTo(5);
        assertThat(rankResponseDto.getRanks().get(2).getArea()).isEqualTo("충청북도 충주시");
    }

    @Test
    public void 테스트_통계데이터_요청() {
        // given
        StatisticsRequestDto requestDto = StatisticsRequestDto.builder().isNationwide(true).isMonthly(true).year(2020).build();
        String url = "http://localhost:"+ port +"/api/v1/statistics/data" + requestDto.toPathString();

        // when
        ResponseEntity<SingleResult<StatisticsDistributedResponseDto>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null ,
                new ParameterizedTypeReference<SingleResult<StatisticsDistributedResponseDto>>() {});

        // then
        System.out.print(responseEntity.toString());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void 테스트_원블록_통계데이터_요청() {
        // given
        StatisticsRequestDto requestDto = StatisticsRequestDto.builder().isNationwide(true).isMonthly(true).year(2020).build();
        String url = "http://localhost:"+ port +"/api/v1/statistics/one_block_data" + requestDto.toPathString();

        // when
        ResponseEntity<SingleResult<StatisticsLinearResponseDto>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null ,
                new ParameterizedTypeReference<SingleResult<StatisticsLinearResponseDto>>() {});

        // then
        System.out.print(responseEntity.toString());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}