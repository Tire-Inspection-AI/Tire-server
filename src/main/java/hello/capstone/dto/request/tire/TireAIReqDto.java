package hello.capstone.dto.request.tire;

import hello.capstone.domain.entity.Tire;
import lombok.Builder;
import lombok.Data;

import java.util.Base64;

@Data
@Builder
public class TireAIReqDto {

    private Long tireId;
    private String imageBase64;

    public static TireAIReqDto of (Tire tire){
        String imageBase64 = null;
        if (tire.getImage() != null) {
            imageBase64 = Base64.getEncoder().encodeToString(tire.getImage());
        }
        return TireAIReqDto.builder()
                .tireId(tire.getId())
                .imageBase64(imageBase64)
                .build();
    }



}
