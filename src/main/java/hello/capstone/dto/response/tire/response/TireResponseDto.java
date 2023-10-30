package hello.capstone.dto.response.tire.response;

import hello.capstone.domain.Car;
import hello.capstone.domain.Tire;
import hello.capstone.dto.response.car.CarResponseDto;
import hello.capstone.util.TirePositionEnum;
import hello.capstone.util.TireStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TireResponseDto {

    /**
     * 간략한 reponse는 타이어 아이디, tirestatus,  tire 포지션만 반환
     * 상세한 response는 사진 + 마모도를? 정확도 까지 반환해야 한다.
     */

    private Long tire_id;
    private TireStatusEnum tireStatus;
    private TirePositionEnum tirePosition;
    private LocalDate LeftFrontTireRecentChangeDate;//년 월 입력

    private double wear; //마모도

    @Data
    @AllArgsConstructor
    @Builder
    public static class TireBrief{
        private Long tire_id;
        private TireStatusEnum tireStatus;
        private TirePositionEnum tirePosition;
    }

    public static TireResponseDto.TireBrief tireBrief(Tire tire){
        return TireBrief.builder()
                .tire_id(tire.getId())
                .tireStatus(tire.getTireStatus())
                .tirePosition(tire.getTirePosition())
                .build();
    }


}