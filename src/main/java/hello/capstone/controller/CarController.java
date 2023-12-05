package hello.capstone.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import hello.capstone.controller.system.Constant;
import hello.capstone.domain.car_api.CarInfo;
import hello.capstone.domain.Message;
import hello.capstone.domain.entity.Car;
import hello.capstone.dto.request.car.CarReqDto;
import hello.capstone.dto.response.car.CarResponseDto;
import hello.capstone.dto.response.car.CarResponseWithTireStatus;
import hello.capstone.service.CarApiService;
import hello.capstone.service.CarService;
import hello.capstone.service.TireWearInspectService;
import hello.capstone.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static hello.capstone.domain.Message.makeMessage;
import static hello.capstone.domain.Message.makeMessage2;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/cars")
public class CarController {

    private final CarService carService;

    private final CarApiService carApiService;

    private final TireWearInspectService tireWearInspectService;

    @PostMapping("")
    public void addCar(HttpServletResponse response, @RequestBody CarReqDto carReqDto) throws Exception {
        ObjectMapper om = ObjectMapperUtil.createObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        CarInfo carInfo = carApiService.getCarInfo(carReqDto);
        CarResponseDto.CarBrief result = carService.savedCar(carInfo, carReqDto);

        Message message = makeMessage(Message.builder()
                .data(result), HttpStatus.OK, Constant.SUCCESS);
        om.writeValue(response.getOutputStream(), message);
    }

    @GetMapping("{carId}")
    public void searchByCarId(@PathVariable Long carId, HttpServletResponse response) throws Exception {
        ObjectMapper om = ObjectMapperUtil.createObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        CarResponseDto result = carService.searchSpecCarInfoByCarId(carId);

        Message message = makeMessage(Message.builder()
                .data(result), HttpStatus.OK, Constant.SUCCESS);
        om.writeValue(response.getOutputStream(), message);
    }

    @GetMapping("{carId}/tires")
    public void searchByCarIdWithTires(@PathVariable Long carId, HttpServletResponse response) throws Exception {
        ObjectMapper om = ObjectMapperUtil.createObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        Car car = carService.searchByCarId(carId);
        car.getTires()
                .forEach(tire -> {
                    tireWearInspectService.inspectOneTireWear(tire.getId());
                });

        CarResponseWithTireStatus result = carService.searchByCarIdWithTires(carId);
        Message message = makeMessage(Message.builder()
                .data(result), HttpStatus.OK, Constant.SUCCESS);
        om.writeValue(response.getOutputStream(), message);
    }

    @DeleteMapping("{carId}")
    public void deleteCarByCarId(@PathVariable Long carId, HttpServletResponse response) throws Exception {
        ObjectMapper om = ObjectMapperUtil.createObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        carService.deleteByCarId(carId);
        Message message = makeMessage(Message.builder().data(null), HttpStatus.OK, Constant.SUCCESS);
        om.writeValue(response.getOutputStream(), message);
    }

    @GetMapping()
    public void searchByUserId(HttpServletResponse response) throws Exception {
        ObjectMapper om = ObjectMapperUtil.createObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        List<CarResponseDto.CarBrief> result = carService.searchByUserId();
        log.info("result={}",result);
        if(result.isEmpty()){
            Message message = makeMessage2(Message.builder()
                    .data(result), -1,HttpStatus.OK, Constant.SUCCESS);
            om.writeValue(response.getOutputStream(), message);
            return;
        }
        Message message = makeMessage(Message.builder()
                .data(result), HttpStatus.OK, Constant.SUCCESS);
        om.writeValue(response.getOutputStream(), message);
    }
}
