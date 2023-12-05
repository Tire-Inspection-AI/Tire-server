package hello.capstone.dto.request.car;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class CarReqDto {

    String regiNumber;//차량 등록 번호

    String ownerName;

    LocalDate FrontLeftTireRecentChangeDate;//년 월 입력

    LocalDate FrontRightTireRecentChangeDate;//년 월 입력

    LocalDate RearLeftTireRecentChangeDate;//년 월 입력

    LocalDate RearRightTireRecentChangeDate;//년 월 입력
}
