package hello.capstone.dto.request.login;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SocialLoginReqDto {

    private String provider;
    private String code;
}
