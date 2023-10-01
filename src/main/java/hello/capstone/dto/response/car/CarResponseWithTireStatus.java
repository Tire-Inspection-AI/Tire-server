package hello.capstone.dto.response.car;


import hello.capstone.domain.Car;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public class CarResponseWithTireStatus {

    /**
     * Car_id로 차량 상세 페이지로 가면, 거기서 인공지능 모델 학습 결과로 타이어 상태 값 얻어서 반환한다.
     * 차량 타이어마다 타이어 id와, 타이어의 상태 값.
     */

    private Long car_id;

    private String type;

    private String model;

    private Integer fourth_digit_license_plate;//차량 번호 뒷 4자리.

    private LocalDate LeftFrontTireRecentChangeDate;//년 월 입력

    private LocalDate LeftBackTireRecentChangeDate;//년 월 입력

    private LocalDate RightFrontTireRecentChangeDate;//년 월 입력

    private LocalDate RightBackTireRecentChangeDate;//년 월 입력

    private LocalDateTime created_at;

    private String leftFrontTireStatus;

    private String leftBackTireStatus;

    private String rightFrontTireStatus;

    private String rightBackTireStatus;


//    public static CarResponseWithTireStatus of(Car car){
//        return CarResponseWithTireStatus.builder()
//                .car_id(car.getId())
//                .type(car.getType().name())
//                .model(car.getModel())
//                .fourth_digit_license_plate(car.getFourth_digit_license_plate())
//                .LeftFrontTireRecentChangeDate(car.getLeftFrontRecentChangeDate())
//                .LeftBackTireRecentChangeDate(car.getLeftBackRecentChangeDate())
//                .RightFrontTireRecentChangeDate(car.getRightFrontRecentChangeDate())
//                .RightBackTireRecentChangeDate(car.getRightBackRecentChangeDate())
//                .created_at(car.getCreatedAt())
//                .leftFrontTireStatus(car.getLeftFrontTire().name())
//                .leftBackTireStatus(car.getLeftBackTire().name())
//                .rightFrontTireStatus(car.getRightFrontTire().name())
//                .rightBackTireStatus(car.getRightBackTire().name())
//                .build();
//    }

}

