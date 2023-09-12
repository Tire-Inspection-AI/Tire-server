package hello.capstone.oauth2;

import hello.capstone.dto.request.login.SocialLoginReqDto;
import hello.capstone.dto.response.login.OauthTokenResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Slf4j
public class OauthTokenProvider {

    public static OauthTokenResponseDto getTokenFromAuthServer(SocialLoginReqDto req, ClientRegistration provider) throws Exception {
        String providerName = req.getProvider();
        String code = req.getCode();

        if (providerName.equals("naver")) {
            return getNaverToken(code, provider);
        }
        else {
            throw new Exception("허용되지 않습니다.");
        }
    }



    public static OauthTokenResponseDto getNaverToken(String code, ClientRegistration provider) {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", provider.getClientId());
        formData.add("client_secret", provider.getClientSecret());
        formData.add("code", code);
        formData.add("state", "1234");

        return WebClient.create()
                .post()
                .uri(provider.getProviderDetails().getTokenUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(OauthTokenResponseDto.class)
                .block();
    }


}