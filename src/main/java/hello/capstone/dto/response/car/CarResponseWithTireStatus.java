package hello.capstone.dto.response.car;


import hello.capstone.domain.entity.Car;
import hello.capstone.dto.response.tire.response.TireResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class CarResponseWithTireStatus {

    /**
     * Car_id로 차량 상세 페이지로 가면, 거기서 인공지능 모델 학습 결과로 타이어 상태 값 얻어서 반환한다.
     * 차량 타이어마다 Brief정보
     * 즉, 차량 정보랑, 타이어 정보를 LIST로 받야야 한다.
     */

    private Long car_id;

    private String name;

    private String registrationNumber;//차량 번호 뒷 4자리.

    private List<TireResponseDto.TireBrief> tires;


    public static CarResponseWithTireStatus of(Car car) {
        List<TireResponseDto.TireBrief> tireBriefs = car.getTires().stream()
                .map(tire -> TireResponseDto.TireBrief.builder()
                        // 여기에서 Tire 엔터티의 필드를 TireBrief로 매핑
                        .tire_id(tire.getId())
                        .tireStatus(tire.getTireStatus())
                        .tirePosition(tire.getTirePosition())
                        .build())
                .collect(Collectors.toList());

        return CarResponseWithTireStatus.builder()
                .car_id(car.getId())
                .name(car.getName())
                .registrationNumber(car.getRegistrationNumber())
                .tires(tireBriefs)
                .build();
    }
}

