package hello.capstone.service;


import hello.capstone.car_api.CarInfo;
import hello.capstone.domain.Car;
import hello.capstone.domain.Tire;
import hello.capstone.domain.User;
import hello.capstone.dto.request.car.CarReqDto;
import hello.capstone.dto.response.car.CarResponseDto;
import hello.capstone.exception.car.CarSavedFailException;
import hello.capstone.exception.car.SearchFailedException;
import hello.capstone.repository.CarRepository;
import hello.capstone.repository.TireRepository;
import hello.capstone.repository.UserRepository;
import hello.capstone.util.SecurityContextHolderUtil;
import hello.capstone.util.TirePositionEnum;
import hello.capstone.util.TireStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {

    private final CarRepository carRepository;

    private final UserRepository userRepository;

    private final TireRepository tireRepository;

    @Transactional
    public CarResponseDto savedCar(CarInfo carInfo, CarReqDto carReqDto){

        try{
            Long userId = SecurityContextHolderUtil.getUserId();
            User user = userRepository.findById(userId).get();

            /**
             * 차량 1대와, 그 차량 번호로 타이어 4개 저장.
             */
            Car newCar = Car.builder()
                    .name(carInfo.getCarName())
                    .vender(carInfo.getCarVender())
                    .registrationNumber(carInfo.getRegistrationNumber())
                    .year(Integer.parseInt(carInfo.getCarYear()))
                    .drive(carInfo.getDrive())
                    .seats(Integer.parseInt(carInfo.getSeats()))
                    .fuel(carInfo.getFuel())
                    .cc(Integer.parseInt(carInfo.getCc()))
                    .fuelEconomy(Double.valueOf(carInfo.getFuelEconomy()))
                    .frontTire(carInfo.getFrontTire())
                    .rearTire(carInfo.getRearTire())
                    .createdAt(LocalDateTime.now())
                    .user(user)
                    .build();

            Car savedCar = carRepository.save(newCar);

            Tire Front_Left = Tire.builder()
                    .recentChangeDate(carReqDto.getFrontLeftTireRecentChangeDate())
                    .tirePosition(TirePositionEnum.Front_Left)
                    .tireStatus(TireStatusEnum.Good_Condition)
                    .car(savedCar)
                    .build();

            Tire savedF_L = tireRepository.save(Front_Left);

            Tire Front_Right = Tire.builder()
                    .recentChangeDate(carReqDto.getFrontRightTireRecentChangeDate())
                    .tirePosition(TirePositionEnum.Front_Right)
                    .tireStatus(TireStatusEnum.Good_Condition)
                    .car(savedCar)
                    .build();

            Tire savedF_R = tireRepository.save(Front_Right);

            Tire Rear_Left = Tire.builder()
                    .recentChangeDate(carReqDto.getRearLeftTireRecentChangeDate())
                    .tirePosition(TirePositionEnum.Rear_Left)
                    .tireStatus(TireStatusEnum.Good_Condition)
                    .car(savedCar)
                    .build();

            Tire savedR_L = tireRepository.save(Rear_Left);

            Tire Rear_Right = Tire.builder()
                    .recentChangeDate(carReqDto.getRearRightTireRecentChangeDate())
                    .tirePosition(TirePositionEnum.Rear_Right)
                    .tireStatus(TireStatusEnum.Good_Condition)
                    .car(savedCar)
                    .build();

            Tire savedR_R = tireRepository.save(Rear_Right);

            return CarResponseDto.of(savedCar);
        }catch(Exception e){
            throw new CarSavedFailException("차량 정보 저장에 실패하였습니다.");
        }


    }
    @Transactional
    public CarResponseDto searchByCarId(Long carId){

            Optional<Car> car = carRepository.findById(carId);
            if (car.isPresent())
                return CarResponseDto.of(car.get());
            else
                throw new SearchFailedException("존재하지 않는 차량입니다.");
    }


}
