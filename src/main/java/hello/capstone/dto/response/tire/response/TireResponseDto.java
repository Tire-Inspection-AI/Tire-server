package hello.capstone.dto.response.tire.response;

import hello.capstone.domain.entity.Tire;
import hello.capstone.domain.TirePositionEnum;
import hello.capstone.domain.TireStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Base64;

@Data
@Builder
public class TireResponseDto {

    private Long tire_id;

    private TireStatusEnum tireStatus;

    private TirePositionEnum tirePosition;

    private LocalDate tireRecentChangeDate;//년 월 입력

    private double wear; //마모도

    private String imageBase64;

    public static TireResponseDto of(Tire tire) {
        String imageBase64 = null;
        if (tire.getImage() != null) {
            imageBase64 = Base64.getEncoder().encodeToString(tire.getImage());
        }
        return TireResponseDto.builder()
                .tire_id(tire.getId())
                .tireStatus(tire.getTireStatus())
                .tirePosition(tire.getTirePosition())
                .tireRecentChangeDate(tire.getRecentChangeDate())
                .wear(tire.getWear())
                .imageBase64(imageBase64)
                .build();
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class TireBrief {
        private Long tire_id;
        private TireStatusEnum tireStatus;
        private TirePositionEnum tirePosition;
    }

    public static TireResponseDto.TireBrief tireBrief(Tire tire) {
        return TireBrief.builder()
                .tire_id(tire.getId())
                .tireStatus(tire.getTireStatus())
                .tirePosition(tire.getTirePosition())
                .build();
    }


}
