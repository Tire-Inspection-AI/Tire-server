package hello.capstone.oauth2;


import hello.capstone.dto.response.login.OauthTokenResponseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

public class OauthUserInfoProvider{

    public static OAuth2UserInfo makeOAuth2UserInfo(String providerName, Map<String, Object> userAttributes) throws Exception {

        if(providerName.equals("naver")){
            return new NaverUserInfo(userAttributes);
        }else if(providerName.equals("kakao")){
            return new KakaoUserInfo(userAttributes);
        }else if(providerName.equals("google")){
            return new GoogleUserInfo(userAttributes);
        } else if (providerName.equals("github")) {
            return new GithubUserInfo(userAttributes);
        } else {
            throw new Exception("허용되지 않은 providerName 입니다.");
        }
    }

    public static Map<String, Object> getUserInfoFromAuthServer(String providerName, ClientRegistration provider, OauthTokenResponseDto tokenResponse) throws Exception {
        if(providerName.equals("naver")) {
            return getNaverUserInfo(provider, tokenResponse);
        }else if(providerName.equals("kakao")){
            return getKakaoUserInfo(provider, tokenResponse);
        }else if(providerName.equals("google")){
            return getGoogleUserInfo(provider,tokenResponse);
        } else if (providerName.equals("github")) {
            return getGithubUserInfo(provider,tokenResponse);
        } else {
            throw new Exception("해당사의 소셜 로그인을 지원하지 않습니다. 다시 입력해주세요.");
        }
    }


    public static Map<String, Object> getNaverUserInfo(ClientRegistration provider, OauthTokenResponseDto tokenResponse){
        Map<String, Object> userAttributes = WebClient.create()
                .get()
                .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
                .headers(header-> header.setBearerAuth(tokenResponse.getAccess_token()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
        return userAttributes;
    }

    public static Map<String, Object> getKakaoUserInfo(ClientRegistration provider, OauthTokenResponseDto tokenResponse){
        Map<String, Object> userAttributes = WebClient.create()
                .get()
                .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
                .headers(header-> header.setBearerAuth(tokenResponse.getAccess_token()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
        return userAttributes;
    }

    public static Map<String, Object> getGoogleUserInfo(ClientRegistration provider, OauthTokenResponseDto tokenResponse){
        Map<String, Object> userAttributes = WebClient.create()
                .get()
                .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
                .headers(header-> header.setBearerAuth(tokenResponse.getAccess_token()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
        return userAttributes;
    }

    public static Map<String, Object> getGithubUserInfo(ClientRegistration provider, OauthTokenResponseDto tokenResponse){
        Map<String, Object> userAttributes = WebClient.create()
                .get()
                .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
                .headers(header-> header.setBearerAuth(tokenResponse.getAccess_token()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
        return userAttributes;
    }

}
