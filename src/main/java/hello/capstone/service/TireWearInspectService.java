package hello.capstone.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.capstone.domain.entity.Tire;
import hello.capstone.dto.request.tire.TireAIReqDto;
import hello.capstone.dto.response.tire.response.TireAIResponseDto;
import hello.capstone.dto.response.tire.response.TireResponseDto;
import hello.capstone.exception.ai.AICommunicationException;
import hello.capstone.exception.tire.TireNotFoundException;
import hello.capstone.repository.TireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class TireWearInspectService {

    private final TireRepository tireRepository;

    private final RestTemplate restTemplate;

    @Value("${ai.server.url}")
    private String aiServerUrl;

    @Transactional
    public TireResponseDto.TireBrief inspectOneTireWear(Long tireId) {
        Tire tire = tireRepository.findById(tireId).orElseThrow(() -> new TireNotFoundException("타이어를 찾을 수 없습니다."));
        return tireInspectAI(tire);
    }

    @Transactional
    public List<TireResponseDto.TireBrief> inspectCarTiresWear(Long carId) {
        List<Tire> tires = tireRepository.findByCarId(carId);
        if (tires.isEmpty()) {
            throw new TireNotFoundException("타이어를 찾을 수 없습니다.");
        }

        return tires.stream()
                .map(this::tireInspectAI)
                .collect(Collectors.toList());

    }

    private TireResponseDto.TireBrief tireInspectAI(Tire tire) {
        TireAIReqDto tireRequest = TireAIReqDto.of(tire);
        String requestJson = convertRequestToJson(tireRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<TireAIResponseDto> response = restTemplate.exchange(
                aiServerUrl, HttpMethod.POST, entity, TireAIResponseDto.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            TireAIResponseDto responseDto = response.getBody();
            log.info("인공지능으로 부터 받은 타이어 아이디는 ={}", responseDto.getTireId());
            log.info("인공지능으로 부터 받은 타이어 상태값 ={}", responseDto.getTireStatusEnum());

            tire.setTireStatus(responseDto.getTireStatusEnum());
            tireRepository.save(tire);

            return new TireResponseDto.TireBrief (responseDto.getTireId(), responseDto.getTireStatusEnum(), tire.getTirePosition());
        }
        throw new AICommunicationException("인공지능 서버와 통신에 실패하였습니다.");
    }


    private String convertRequestToJson(TireAIReqDto tireRequest) {
        try {
            return new ObjectMapper().writeValueAsString(tireRequest);
        } catch (Exception e) {
            // JSON 직렬화 중 예외 처리
            throw new RuntimeException("json으로 request 변환 실패");
        }
    }
}
