package hello.capstone.controller;


import hello.capstone.service.TireWearInspectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/tirewear")
public class TireWearController {

    private final TireWearInspectService tireWearInspectService;
    @GetMapping("/{tireId}")
    public void inspectTireWear(@PathVariable("tireId") Long tireId){
        tireWearInspectService.inspectTireWear(tireId);
    }

}
