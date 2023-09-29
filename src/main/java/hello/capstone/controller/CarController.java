package hello.capstone.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hello.capstone.domain.Message;
import hello.capstone.dto.request.car.NewCarReqDto;
import hello.capstone.dto.response.car.CarResponseDto;
import hello.capstone.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/cars")
public class CarController {

    private final CarService carService;

    @PostMapping("")
    public void addCar(HttpServletResponse response, @RequestBody NewCarReqDto newCarReqDto) throws IOException {

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());

        response.setContentType(MediaType.APPLICATION_JSON.toString());

        CarResponseDto result = carService.savedCar(newCarReqDto);

        Message message = Message.builder()
                .data(result)
                .status(HttpStatus.OK)
                .message("success")
                .build();

        om.writeValue(response.getOutputStream(), message);
    }
}
