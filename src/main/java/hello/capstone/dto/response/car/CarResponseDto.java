package hello.capstone.dto.response.car;

import hello.capstone.domain.Car;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Builder
@Getter
public class CarResponseDto {

    private Long car_id;

    private String type;

    private String model;

    private Integer fourth_digit_license_plate;//차량 번호 뒷 4자리.

    private LocalDate LeftFrontTireRecentChangeDate;//년 월 입력

    private LocalDate LeftBackTireRecentChangeDate;//년 월 입력

    private LocalDate RightFrontTireRecentChangeDate;//년 월 입력

    private LocalDate RightBackTireRecentChangeDate;//년 월 입력

    private LocalDateTime created_at;

    public static CarResponseDto of(Car car){
        return CarResponseDto.builder()
                .car_id(car.getId())
                .type(car.getType().name())
                .model(car.getModel())
                .fourth_digit_license_plate(car.getFourth_digit_license_plate())
                .LeftFrontTireRecentChangeDate(car.getLeftFrontRecentChangeDate())
                .LeftBackTireRecentChangeDate(car.getLeftBackRecentChangeDate())
                .RightFrontTireRecentChangeDate(car.getRightFrontRecentChangeDate())
                .RightBackTireRecentChangeDate(car.getRightBackRecentChangeDate())
                .created_at(car.getCreatedAt())
                .build();
    }


}
