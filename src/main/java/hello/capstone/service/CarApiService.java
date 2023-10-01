package hello.capstone.service;


import hello.capstone.car_api.CarInfo;
import hello.capstone.car_api.CarInfoProvider;
import hello.capstone.dto.request.car.CarReqDto;
import hello.capstone.exception.car.NotFoundException;
import hello.capstone.exception.car.OwnerMismatchException;
import hello.capstone.exception.car.SearchFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarApiService {

    private final CarInfoProvider carInfoProvider;

    public CarInfo getCarInfo(CarReqDto req) throws Exception{

        //받은 정보를 바탕으로 CarInfoResponseDto 만든다.
        Map<String, Object> attributes = carInfoProvider.getCarInfo(req);

        /**
         * 예외 처리하자.
         */
        log.info("attrbutes={}",attributes.get("errCode"));
        log.info("attrbutes={}",attributes.get("errMsg"));

        String errCode = (String) attributes.get("errCode");
        String errMsg = (String) attributes.get("errMsg");
        Map<String, Object> data = (Map<String, Object>) attributes.get("data");

        if(errCode.equals("6112")){//소유자 정보가 맞지 않을떼
            throw new OwnerMismatchException("소유자 정보가 맞지 않습니다.");
        }

        else if(errCode.equals("6114")){
            throw new NotFoundException("차량 번호로 조회된 내역이 없습니다.");
        }

        else if(data==null){
            throw new SearchFailedException("조회에 실패햐였습니다.");
        }

        CarInfo carInfo = carInfoProvider.makeCarInfo(attributes);
        carInfo.setRegistrationNumber(req.getRegiNumber());
        return carInfo;
    }


}
