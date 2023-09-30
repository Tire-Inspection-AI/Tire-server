package hello.capstone.service;


import hello.capstone.domain.Car;
import hello.capstone.domain.User;
import hello.capstone.dto.request.car.NewCarReqDto;
import hello.capstone.dto.response.car.CarResponseDto;
import hello.capstone.exception.car.CarSavedFailException;
import hello.capstone.repository.CarRepository;
import hello.capstone.repository.UserRepository;
import hello.capstone.util.SecurityContextHolderUtil;
import hello.capstone.util.TireStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {

    private final CarRepository carRepository;

    private final UserRepository userRepository;

    @Transactional
    public CarResponseDto savedCar(NewCarReqDto newCarReqDto){

        try{
            Long userId = SecurityContextHolderUtil.getUserId();
            User user = userRepository.findById(userId).get();

            Car newCar= Car.builder()
                    .type(newCarReqDto.getType())
                    .model(newCarReqDto.getModel())
                    .fourth_digit_license_plate(newCarReqDto.getFourth_digit_license_plate())
                    .LeftFrontRecentChangeDate(newCarReqDto.getLeftFrontTireRecentChangeDate())
                    .LeftBackRecentChangeDate(newCarReqDto.getLeftBackTireRecentChangeDate())
                    .RightFrontRecentChangeDate(newCarReqDto.getRightFrontTireRecentChangeDate())
                    .RightBackRecentChangeDate(newCarReqDto.getRightBackTireRecentChangeDate())
                    .createdAt(LocalDateTime.now())
                    .LeftFrontTire(TireStatusEnum.GoodCondition)
                    .LeftBackTire(TireStatusEnum.GoodCondition)
                    .RightFrontTire(TireStatusEnum.GoodCondition)
                    .RightBackTire(TireStatusEnum.GoodCondition)
                    .user(user)
                    .build();

            Car savedCar = carRepository.save(newCar);
            return CarResponseDto.of(savedCar);
        }catch(Exception e){
            throw new CarSavedFailException("차량 정보 저장에 실패하였습니다.");
        }


    }

}
