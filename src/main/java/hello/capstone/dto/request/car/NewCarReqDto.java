package hello.capstone.dto.request.car;


import hello.capstone.util.CarTypeEnum;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class NewCarReqDto {

    private CarTypeEnum type;

    private String model;

    private Integer fourth_digit_license_plate;//차량 번호 뒷 4자리.

    private LocalDate recentChangeDate;//월 일

}
