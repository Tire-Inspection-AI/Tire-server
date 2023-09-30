package hello.capstone.service;


import hello.capstone.car_api.CarInfo;
import hello.capstone.car_api.CarInfoProvider;
import hello.capstone.dto.request.car_api.CarApiReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarApiService {

    private final CarInfoProvider carInfoProvider;

    public CarInfo getCarInfo(CarApiReqDto req) throws Exception{

        //받은 정보를 바탕으로 CarInfoResponseDto 만든다.
        Map<String, Object> attributes = carInfoProvider.getCarInfo(req);
        CarInfo carInfo = carInfoProvider.makeCarInfo(attributes);
        return carInfo;
    }


}
