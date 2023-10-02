package hello.capstone.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hello.capstone.car_api.CarInfo;
import hello.capstone.domain.Message;
import hello.capstone.dto.request.car.CarReqDto;
import hello.capstone.dto.response.car.CarResponseDto;
import hello.capstone.service.CarApiService;
import hello.capstone.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/cars")
public class CarController {

    private final CarService carService;

    private final CarApiService carApiService;

    @PostMapping("")
    public void addCar(HttpServletResponse response, @RequestBody CarReqDto carReqDto) throws Exception {


        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());

        response.setContentType(MediaType.APPLICATION_JSON.toString());

        CarInfo carInfo = carApiService.getCarInfo(carReqDto);
        CarResponseDto.CarBrief result = carService.savedCar(carInfo,carReqDto);

        Message message = Message.builder()
                .data(result)
                .status(HttpStatus.OK)
                .message("success")
                .build();

        om.writeValue(response.getOutputStream(), message);
    }

    @GetMapping("{carId}")
    public void searchByCarId(@PathVariable Long carId, HttpServletResponse response) throws Exception {

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        CarResponseDto result = carService.searchByCarId(carId);
        Message message = Message.builder()
                .data(result)
                .status(HttpStatus.OK)
                .message("success")
                .build();

        om.writeValue(response.getOutputStream(), message);
    }

    @DeleteMapping("{carId}")
    public void deleteCarByCarId(@PathVariable Long carId, HttpServletResponse response) throws Exception {

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        carService.deleteByCarId(carId);
        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();

        om.writeValue(response.getOutputStream(), message);
    }

    @GetMapping()
    public void searchByUserId(HttpServletResponse response) throws Exception {


        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        response.setContentType(MediaType.APPLICATION_JSON.toString());


        List<CarResponseDto.CarBrief> result = carService.searchByUserId();
        Message message = Message.builder()
                .data(result)
                .status(HttpStatus.OK)
                .message("success")
                .build();
        om.writeValue(response.getOutputStream(), message);
    }
}
