package hello.capstone.domain.car_api;


import hello.capstone.dto.request.car.CarReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
public class CarInfoProvider {

    @Value("${capstone.api.token}")
    private String token;

    @Value("${capstone.api.uri}")
    private String apiUri;

    public Map<String, Object> getCarInfo(CarReqDto carApiReqDto){

        //요청 바디 생성.
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("REGINUMBER", carApiReqDto.getRegiNumber());
        requestBody.put("OWNERNAME", carApiReqDto.getOwnerName());

        //WebClient를 이용한 POST 요청

        WebClient.ResponseSpec responseSpec = WebClient.create()
                .post()
                .uri(apiUri)
                .header(HttpHeaders.AUTHORIZATION, "Token " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve();

        Map<String, Object> response = responseSpec.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        return response;
    }

    public CarInfo makeCarInfo(Map<String, Object> attributes) {

        if (attributes != null) {
            Map<String, Object> data = (Map<String, Object>) attributes.get("data");
            if (data != null) {
                return CarInfo.builder()
                        .drive((String) data.get("DRIVE"))
                        .fuel((String) data.get("FUEL"))
                        .carName((String) data.get("CARNAME"))
                        .carVender((String) data.get("CARVENDER"))
                        .frontTire((String) data.get("FRONTTIRE"))
                        .rearTire((String) data.get("REARTIRE"))
                        .seats((String) data.get("SEATS"))
                        .fuelEconomy((String) data.get("FUELECO"))
                        .carYear((String) data.get("CARYEAR"))
                        .cc((String) data.get("CC"))
                        .build();
            }
        }
        return null;
    }
}
