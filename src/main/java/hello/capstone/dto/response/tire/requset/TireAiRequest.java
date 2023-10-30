package hello.capstone.dto.response.tire.requset;

import lombok.Data;

@Data
public class TireAiRequest {
    private String imageBase64;//이미지 데이터를 Base64로 인코딩된 문자열로 전송함
}
