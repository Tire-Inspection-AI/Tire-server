package hello.capstone.car_api;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CarInfo {

    private String carName;//차량 명

    private String carVender;//제조사

    private String carYear;//차대번호상의 년식

    private String registrationNumber;//차량 번호.

    private String drive;//구동방식

    private String seats;//좌석 수

    private String fuel;//연료

    private String cc;//배기량

    private String fuelEconomy;//연비
    private String frontTire;//앞 타이어 정보
    private String rearTire;//뒤 타이어 정보




}
