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

    private LocalDate recentChangeDate;//월 일

    private LocalDateTime created_at;

    public static CarResponseDto of(Car car){
        return CarResponseDto.builder()
                .car_id(car.getId())
                .type(car.getType().name())
                .model(car.getModel())
                .fourth_digit_license_plate(car.getFourth_digit_license_plate())
                .recentChangeDate(car.getRecentChangeDate())
                .created_at(car.getCreatedAt())
                .build();
    }


}
