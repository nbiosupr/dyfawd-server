package site.deepsleep.dyfawd.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.deepsleep.dyfawd.service.SensorService;
import site.deepsleep.dyfawd.web.dto.SensorDataSaveRequestDto;
import site.deepsleep.dyfawd.web.dto.response.CommonResult;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class SensorApiController {

    private final SensorService sensorService;

    @PostMapping("/sensordata")
    public CommonResult save(@RequestBody SensorDataSaveRequestDto requestDto) {
        return sensorService.save(requestDto);
    }
}
