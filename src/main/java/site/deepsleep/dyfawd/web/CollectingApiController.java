package site.deepsleep.dyfawd.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.deepsleep.dyfawd.service.CollectingService;
import site.deepsleep.dyfawd.web.dto.DataSaveRequestDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/data")
public class CollectingApiController {

    private final CollectingService collectingService;

    @PostMapping("/")
    public Long save(@RequestBody DataSaveRequestDto requestDto) {
        return collectingService.save(requestDto);
    }

}
