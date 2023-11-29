package hello.capstone.dto.response.car;

import hello.capstone.domain.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import java.time.LocalDateTime;


@Builder
@Getter
public class CarResponseDto {

    /**
     * 차량 정보 반환.
     */
    private Long car_id;
    private String name;

    private String vender;

    private String registrationNumber;

    private Integer year;

    private String drive;

    private Integer seats;

    private String fuel;

    private Integer cc;

    private double fuelEconomy;

    private String frontTire;

    private String rearTire;

    private LocalDateTime createdAt;

    public static CarResponseDto of(Car car){
        return CarResponseDto.builder()
                .car_id(car.getId())
                .name(car.getName())
                .vender(car.getVender())
                .registrationNumber(car.getRegistrationNumber())
                .year(car.getYear())
                .drive(car.getDrive())
                .seats(car.getSeats())
                .fuel(car.getFuel())
                .cc(car.getCc())
                .fuelEconomy(car.getFuelEconomy())
                .frontTire(car.getFrontTire())
                .rearTire(car.getRearTire())
                .createdAt(car.getCreatedAt())
                .build();
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class CarBrief{
        private Long car_id;
        private String name;

        private String vender;

        private String registrationNumber;

    }


    public static CarResponseDto.CarBrief carBrief(Car car){
        return CarResponseDto.CarBrief.builder()
                .car_id(car.getId())
                .name(car.getName())
                .vender(car.getVender())
                .registrationNumber(car.getRegistrationNumber())
                .build();
    }

}
