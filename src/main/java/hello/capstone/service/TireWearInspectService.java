package hello.capstone.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.capstone.domain.Tire;
import hello.capstone.dto.request.tire.TireAIReqDto;
import hello.capstone.dto.response.tire.response.TireAIResponseDto;
import hello.capstone.exception.tire.TireNotFoundException;
import hello.capstone.repository.TireRepository;
import hello.capstone.util.TireStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
@Slf4j
public class TireWearInspectService {


    private final TireRepository tireRepository;

    @Transactional
    public void inspectTireWear(Long tireId){

        /**
         * AI 주소로 데이터를 보내고 response를 얻어야 하는데,
         * "110.25.03.234:5000"의 /api/wea 로 요청을 보내고 응답을 얻어와서 tireAIRequestDto로 변환해야해.
         *
         */

        Tire tire;
        try {
            tire = tireRepository.findById(tireId).orElseThrow(() -> new TireNotFoundException("타이어를 찾을 수 없습니다."));
            log.info("타이어를 find함");

            // ... 나머지 코드 ...
        } catch (Exception e) {
            log.error("에러 발생: {}", e.getMessage(), e);
            throw e; // 에러를 다시 throw하여 롤백되도록 함
        }
        TireAIReqDto tireRequest = TireAIReqDto.of(tire);
        String requestJson = convertRequestToJson(tireRequest);
        log.info("타이어 보낼 string만듬");
        RestTemplate restTemplate = new RestTemplate();
        String aiServerUrl = "http://192.168.1.184:8081/api/wear";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<TireAIResponseDto> response = restTemplate.exchange(aiServerUrl, HttpMethod.POST, entity, TireAIResponseDto.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            TireAIResponseDto responseDto = response.getBody();
            log.info("인공지능으로 부터 받은 타이어 아이디는 ={}",responseDto.getTireId());
            log.info("인공지능으로 부터 받은 타이어 상태값 ={}",responseDto.getTireStatusEnum());
            tire.setTireStatus(responseDto.getTireStatusEnum());
            // 타이어 업데이트 및 저장
            tireRepository.save(tire);
        } else {
            throw new RuntimeException("AI 서버에서 오류 응답을 받았습니다. 상태 코드: " + response.getStatusCode());
        }

//        TireAIResponseDto tireAIResponseDto = new TireAIResponseDto(tireId, TireStatusEnum.Replace_Required);
//        tire.setTireStatus(tireAIResponseDto.getTireStatusEnum());


    }

    private static String convertRequestToJson(TireAIReqDto tireRequest) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(tireRequest);
        } catch (Exception e) {
            // JSON 직렬화 중 예외 처리
            e.printStackTrace();
            throw new RuntimeException("json으로 request 변환 실패");
        }
    }

    private static TireAIResponseDto convertJsonToResponseDto(ResponseEntity<String> response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = response.getBody(); // JSON 문자열 가져오기

            // JSON 문자열을 TireAIResponseDto로 변환
            TireAIResponseDto responseDto = objectMapper.readValue(json, TireAIResponseDto.class);

            return responseDto;
        } catch (Exception e) {
            // JSON 역직렬화 중 예외 처리
            e.printStackTrace();
            throw new RuntimeException("JSON을 TireAIResponseDto로 변환 실패");
        }
    }
}
