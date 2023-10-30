package hello.capstone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hello.capstone.domain.Message;
import hello.capstone.dto.response.car.CarResponseDto;
import hello.capstone.dto.response.tire.response.TireResponseDto;
import hello.capstone.service.TireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/tires")
public class TireController {

    private  final TireService tireService;
    @GetMapping("{tireId}")
    public void searchByTireId(@PathVariable Long tireId, HttpServletResponse response) throws Exception {

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        TireResponseDto result = tireService.searchByTireId(tireId);
        Message message = Message.builder()
                .data(result)
                .status(HttpStatus.OK)
                .message("success")
                .build();

        om.writeValue(response.getOutputStream(), message);
    }
}
