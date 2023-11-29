package hello.capstone.service;


import hello.capstone.domain.car_api.CarInfo;
import hello.capstone.domain.entity.Car;
import hello.capstone.domain.entity.Tire;
import hello.capstone.domain.entity.User;
import hello.capstone.dto.request.car.CarReqDto;
import hello.capstone.dto.response.car.CarResponseDto;
import hello.capstone.dto.response.car.CarResponseWithTireStatus;
import hello.capstone.exception.car.CarDeleteFailException;
import hello.capstone.exception.car.CarSavedFailException;
import hello.capstone.exception.car.SearchFailedException;
import hello.capstone.exception.user.UserNotFoundException;
import hello.capstone.repository.CarRepository;
import hello.capstone.repository.TireRepository;
import hello.capstone.repository.UserRepository;
import hello.capstone.util.SecurityContextHolderUtil;
import hello.capstone.domain.TirePositionEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static hello.capstone.domain.entity.Tire.createTire;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {

    private final CarRepository carRepository;

    private final UserRepository userRepository;

    private final TireRepository tireRepository;

    @Transactional
    public CarResponseDto.CarBrief savedCar(CarInfo carInfo, CarReqDto carReqDto) {

        try {
            Long userId = SecurityContextHolderUtil.getUserId();
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 업습니다."));

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

            List<Tire> tires = Arrays.asList(
                    createTire(savedCar, TirePositionEnum.Front_Right, carReqDto.getFrontRightTireRecentChangeDate()),
                    createTire(savedCar, TirePositionEnum.Front_Left, carReqDto.getFrontLeftTireRecentChangeDate()),
                    createTire(savedCar, TirePositionEnum.Rear_Left, carReqDto.getRearLeftTireRecentChangeDate()),
                    createTire(savedCar, TirePositionEnum.Rear_Right, carReqDto.getRearRightTireRecentChangeDate())
            );

            tireRepository.saveAll(tires);

            return CarResponseDto.carBrief(savedCar);
        } catch (Exception e) {
            throw new CarSavedFailException("차량 정보 저장에 실패하였습니다.");
        }

    }

    @Transactional
    public CarResponseDto searchSpecCarInfoByCarId(Long carId) {

        Long userId = SecurityContextHolderUtil.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        Car car = user.getCars().stream()
                .filter(c -> c.getId().equals(carId))
                .findFirst()
                .orElseThrow(() -> new SearchFailedException("접근 권한이 없는 차량입니다."));
        return CarResponseDto.of(car);

    }

    @Transactional
    public Car searchByCarId(Long carId) {
        Optional<Car> carOptional = carRepository.findByCarId(carId);
        return carOptional.orElseThrow(() -> new SearchFailedException("차량 검색에 실패하였습니다."));
    }

    @Transactional
    public CarResponseWithTireStatus searchByCarIdWithTires(Long carId) {//Car의 간략한 정보와, Tires의 정보들을 보여준다.

        Long userId = SecurityContextHolderUtil.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        Car car = user.getCars().stream()
                .filter(c -> c.getId().equals(carId))
                .findFirst()
                .orElseThrow(() -> new SearchFailedException("접근 권한이 없는 차량입니다."));
        log.info("car.getTires={}", car.getTires());
        return CarResponseWithTireStatus.of(car);
    }

    @Transactional
    public void deleteByCarId(Long carId) {

        Long userId = SecurityContextHolderUtil.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        Optional<Car> carToDelete = user.getCars().stream()
                .filter(car -> car.getId().equals(carId))
                .findFirst();

        carToDelete.ifPresent(car -> {
            carRepository.delete(car);
            user.deleteCar(car);
        });

        if (carToDelete.isEmpty())
            throw new CarDeleteFailException("차량 삭제에 실패했습니다.");
    }

    @Transactional
    public List<CarResponseDto.CarBrief> searchByUserId() {

        Long userId = SecurityContextHolderUtil.getUserId();
        List<Car> cars = carRepository.findByUserId(userId);

        return cars.stream()
                .map(CarResponseDto::carBrief)
                .collect(Collectors.toList());
    }
}
