package site.deepsleep.dyfawd.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.deepsleep.dyfawd.service.SensorDataService;
import site.deepsleep.dyfawd.web.dto.SensorDataSaveRequestDto;
import site.deepsleep.dyfawd.web.dto.response.CommonResult;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/sensor/")
public class SensorApiController {

    private final SensorDataService sensorDataService;

    @PostMapping("/data")
    public CommonResult save(@RequestBody SensorDataSaveRequestDto requestDto) {
        return sensorDataService.save(requestDto);
    }
}
