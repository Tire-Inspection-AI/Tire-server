package hello.capstone.dto.response.tire.response;

import hello.capstone.domain.TireStatusEnum;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
public class TireAIResponseDto {

    private Long tireId;
    private String tireStatusEnum;

    public TireAIResponseDto(Long tireId, String tireStatusEnum) {
        this.tireId = tireId;
        this.tireStatusEnum = tireStatusEnum;
    }
    public TireStatusEnum getTireStatusEnum() {
        return TireStatusEnum.fromValue(tireStatusEnum);
    }
}