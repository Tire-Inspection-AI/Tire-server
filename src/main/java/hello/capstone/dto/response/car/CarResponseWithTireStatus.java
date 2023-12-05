package hello.capstone.dto.response.car;


import hello.capstone.domain.entity.Car;
import hello.capstone.dto.response.tire.response.TireResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CarResponseWithTireStatus {

    private Long car_id;

    private String name;

    private String registrationNumber;//차량 번호 뒷 4자리.

    private List<TireResponseDto.TireBrief> tires;

    public static CarResponseWithTireStatus of(Car car, List<TireResponseDto.TireBrief> tires) {
        return CarResponseWithTireStatus.builder()
                .car_id(car.getId())
                .name(car.getName())
                .registrationNumber(car.getRegistrationNumber())
                .tires(tires)
                .build();
    }
}

