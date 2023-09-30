package hello.capstone.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hello.capstone.car_api.CarInfo;
import hello.capstone.domain.Message;
import hello.capstone.dto.request.car_api.CarApiReqDto;
import hello.capstone.service.CarApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/car-Information")
@RequiredArgsConstructor
public class CarInformationApiController {

    private final CarApiService carApiService;

    @PostMapping("")
    public void findCarInfo(HttpServletResponse response, @RequestBody CarApiReqDto carApiReqDto) throws IOException {

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        CarInfo carInfo = null;
        try {
            carInfo = carApiService.getCarInfo(carApiReqDto);
        } catch (Exception e) {
            Message message = Message.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(e.getMessage())
                    .build();
            om.writeValue(response.getOutputStream(), message);
            return;
        }
        Message message = Message.builder()
                .status(HttpStatus.OK)
                .data(carInfo)
                .message("소셜 로그인 성공. 엑세스 토큰을 발급힙니다.")
                .build();
        om.writeValue(response.getOutputStream(), message);
    }
}
