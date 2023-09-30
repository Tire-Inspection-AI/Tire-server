package hello.capstone.dto.request.car_api;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CarApiReqDto {

    String regiNumber;//차량 등록 번호

    String ownerName;
}
