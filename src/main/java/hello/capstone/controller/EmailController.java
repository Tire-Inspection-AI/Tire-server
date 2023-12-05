package hello.capstone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.capstone.controller.system.Constant;
import hello.capstone.domain.Message;
import hello.capstone.dto.request.email.EmailCodeReqDto;
import hello.capstone.dto.request.email.EmailRequestDto;
import hello.capstone.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static hello.capstone.domain.Message.makeMessage;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final EmailService emailService;

    @PostMapping("login/mailConfirm")
    public void mailConfirm(HttpServletResponse response, @RequestBody EmailRequestDto emailDto) throws MessagingException, IOException {
        ObjectMapper om = new ObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        String authCode = emailService.sendMailCodeAndSave(emailDto);

        Message message = makeMessage(Message.builder().data(null), HttpStatus.OK, Constant.SUCCESS);
        om.writeValue(response.getOutputStream(), message);
    }

    @PostMapping("login/authenticate")
    public void mailCodeAuthenticate(HttpServletResponse response, @RequestBody EmailCodeReqDto emailCodeReqDto) throws IOException {
        ObjectMapper om = new ObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        emailService.mailCodeAuthenticate(emailCodeReqDto);

        Message message = makeMessage(Message.builder().data(null), HttpStatus.OK, Constant.SUCCESS);
        om.writeValue(response.getOutputStream(), message);
    }
}