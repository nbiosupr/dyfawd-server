package site.deepsleep.dyfawd.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.deepsleep.dyfawd.service.SensorService;
import site.deepsleep.dyfawd.web.dto.SensorDataSaveRequestDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class SensorApiController {

    private final SensorService sensorService;

    @PostMapping("/sensordata")
    public Long save(@RequestBody SensorDataSaveRequestDto requestDto) {
        return sensorService.save(requestDto);
    }

}
