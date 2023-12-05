package hello.capstone.service;

import hello.capstone.domain.entity.EmailTmp;
import hello.capstone.dto.request.email.EmailCodeReqDto;
import hello.capstone.dto.request.email.EmailRequestDto;
import hello.capstone.exception.email.EmailCodeExpiredException;
import hello.capstone.exception.email.EmailCodeMismatchException;
import hello.capstone.exception.email.EmailCodeSendingFailureException;
import hello.capstone.repository.EmailTmpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final EmailTmpRepository emailTmpRepository;

    private final EmailSendingService emailSendingService;


    @Transactional
    public void save(EmailTmp emailTmp) {
        emailTmpRepository.save(emailTmp);
    }

    @Transactional(rollbackFor = EmailCodeSendingFailureException.class)
    public String sendMailCodeAndSave(EmailRequestDto emailRequestDto) throws MessagingException {

        Optional<EmailTmp> findByUserEmail = emailTmpRepository.findByUserEmail(emailRequestDto.getEmail());
        findByUserEmail.ifPresent(emailTmpRepository::delete);

        String authCode = emailSendingService.SendEmail(emailRequestDto.getEmail());//인증번호 전송과, emailTmp 저장을 transaction으로 묶음.

        //db에 사용자가 이미 먼저 보낸 인증 코드가 있으면, 그 레코드 삭제하고, 새로 생성.
        EmailTmp emailTmp = EmailTmp.builder()
                .userEmail(emailRequestDto.getEmail())
                .code(authCode)
                .createdAt(LocalDateTime.now())
                .build();
        save(emailTmp);
        return authCode;
    }

    public void mailCodeAuthenticate(EmailCodeReqDto emailCodeReqDto) {

        Optional<EmailTmp> findUserEmail = emailTmpRepository.findByUserEmail(emailCodeReqDto.getEmail());
        if (findUserEmail.isEmpty()) {
            throw new EmailCodeExpiredException("인증번호가 만료되었습니다.! 다시 전송해주세요.");
        }
        String code = findUserEmail.get().getCode();
        log.info("code={}", code);
        if (!emailCodeReqDto.getCode().equals(code)) {
            throw new EmailCodeMismatchException("이메일 코드 틀림. 다시 입력해주세요");
        }
    }


}