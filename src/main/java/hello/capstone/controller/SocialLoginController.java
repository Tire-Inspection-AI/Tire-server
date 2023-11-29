package hello.capstone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hello.capstone.controller.system.Constant;
import hello.capstone.domain.Message;
import hello.capstone.dto.request.login.SocialLoginReqDto;
import hello.capstone.dto.response.login.LoginResponseDto;
import hello.capstone.dto.response.user.UserResponseDto;
import hello.capstone.service.SocialLoginService;
import hello.capstone.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static hello.capstone.domain.Message.makeMessage;

@RestController
@RequestMapping("/api/social-login")
@RequiredArgsConstructor
public class SocialLoginController {

    private final SocialLoginService socialLoginService;

    @PostMapping("")
    public void socialLogin(HttpServletResponse response, @RequestBody SocialLoginReqDto req) throws IOException {
        ObjectMapper om = ObjectMapperUtil.createObjectMapper();
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        LoginResponseDto loginResponse = null;
        try {
            loginResponse = socialLoginService.socialLogin(req);
        } catch (Exception e) {
            Message message = makeMessage(Message.builder()
                    .data(null), HttpStatus.BAD_REQUEST, e.getMessage());
            om.writeValue(response.getOutputStream(), message);
            return;
        }
        Message message = makeMessage(Message.builder()
                .data(UserResponseDto.of(loginResponse.getUser())), HttpStatus.OK, Constant.SOCIAL_LOGIN_SUCCESS);

        String accessToken = loginResponse.getAccess_token();

        ResponseCookie cookie = ResponseCookie.from("tire_token", accessToken)
                .httpOnly(true)
                .sameSite("None")
                .secure(true)
                .path("/")
                .maxAge(60 * 60 * 3) // 3시간
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        om.writeValue(response.getOutputStream(), message);
    }
}