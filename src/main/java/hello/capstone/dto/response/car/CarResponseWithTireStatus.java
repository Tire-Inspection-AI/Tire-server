package hello.capstone.dto.response.car;


import hello.capstone.domain.Tire;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public class CarResponseWithTireStatus {

    /**
     * Car_id로 차량 상세 페이지로 가면, 거기서 인공지능 모델 학습 결과로 타이어 상태 값 얻어서 반환한다.
     * 차량 타이어마다 Brief정보
     * 즉, 차량 정보랑, 타이어 정보를 LIST로 받야야 한다.
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

    private List<Tire> tires;

}

