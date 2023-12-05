package hello.capstone.service;

import hello.capstone.domain.entity.EmailTmp;
import hello.capstone.repository.EmailTmpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailSendingService {

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine templateEngine;

    private final EmailTmpRepository emailTmpRepository;

    public String createCode(){
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            key.append(random.nextInt(9));

        }
        return key.toString();
    }



    public String SendEmail(String email) throws MessagingException {
        String authNum = createCode();
        String setFrom = "gntjd135@gmail.com";
        String toEmail = email; //받는 사람
        String title = "Tire Inspection 회원가입 인증 번호 " + "[" + authNum + "]";

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");

        mimeMessageHelper.setTo(toEmail); // 수신자 설정
        mimeMessageHelper.setSubject(title);
        mimeMessageHelper.setFrom(setFrom);
        mimeMessageHelper.setText(setContext(authNum), true); // true: HTML 포맷 사용

        String imageResourceName = "tire.png"; // 이미지 파일명
        ClassPathResource imageResource = new ClassPathResource("templates/" + imageResourceName);
        mimeMessageHelper.addInline(imageResourceName, imageResource);

        //메일 전송에 필요한 정보 설정 후 , 실제 이메일 전송.
        emailSender.send(message);

        return authNum;//인증 코드 반환.
    }


    //타임 리프를 이용한 context 설정
    public String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("mail", context);//mail.html
    }

    public Optional<EmailTmp> findByUserEmail(String userEmail){
        return emailTmpRepository.findByUserEmail(userEmail);
    }

}
