package hello.capstone.service;


import hello.capstone.domain.Tire;
import hello.capstone.domain.User;
import hello.capstone.dto.response.tire.response.TireResponseDto;
import hello.capstone.exception.tire.TireNotFoundException;
import hello.capstone.exception.user.UserNotFoundException;
import hello.capstone.repository.TireRepository;
import hello.capstone.repository.UserRepository;
import hello.capstone.util.SecurityContextHolderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TireService {

    /**
     * 바퀴의 사진을 모델에 넘기고, 학습시킨 결과(마모도, tirestatus)를 얻어오고 사진과 함꼐 프론트에 반환해야 한다.
     *
     * 1.헉습시킨 결과인 TireBrief 정보만 프론트한테 준다. 반환 타입: TireResponseDto.tireBrief
     * 2.합습시킨 결과 + 사진, 최근 타이어 교체일 같은 상세 정보까지 프론트에 준다.  반환 타입:
     */
    private final UserRepository userRepository;

    private final TireRepository tireRepository;

    @Transactional
    public void saveTireImage(Long tireId, MultipartFile imageFile){
        //이미지 파일을 바이트 배열로 변환
        byte[] imageBytes;
        try{
            imageBytes = imageFile.getBytes();
        }catch (IOException e){
            throw new RuntimeException("사진을 읽는데 실패하였습니다.");
        }

        Tire tire = tireRepository.findById(tireId)
                .orElseThrow(() -> new TireNotFoundException("타이어가 존재하지 않습니다."));

        //이미지 데이터 설정
        tire.setImage(imageBytes);
    }

    @Transactional
    public TireResponseDto searchByTireId(Long tireId){

        Long userId = SecurityContextHolderUtil.getUserId();
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("사용자를 찾을 수 업습니다."));

        Optional<Tire> optionalTire = user.getCars().stream()
                .flatMap(car -> car.getTires().stream())
                .filter(tire -> tire.getId() == tireId)
                .findFirst();

        Tire tire = optionalTire.orElseThrow(() -> new TireNotFoundException("접근할 수 없는 타이어 정보 입니다."));
        return TireResponseDto.of(tire);
    }
}
