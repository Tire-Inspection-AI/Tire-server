package hello.capstone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.capstone.controller.system.Constant;
import hello.capstone.domain.Message;
import hello.capstone.dto.response.tire.response.TireResponseDto;
import hello.capstone.service.TireService;
import hello.capstone.service.TireWearInspectService;
import hello.capstone.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static hello.capstone.domain.Message.makeMessage;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/tires")
public class TireController {

    private final TireService tireService;

    private final TireWearInspectService tireWearInspectService;

    /** 블루 투스로부터 (타이어 id, 타이어 사진)을 전송받아 db에 저장하고,
     * AI에 넣어서 학습 돌리는 로직이 작성되어야 한다.
     * 이 내용이 아래의 함수이다.
     */

    @PostMapping("/{tireId}/image")
    public void saveTireImageAndInspect(@RequestParam("image") MultipartFile imageFile, @PathVariable Long tireId, HttpServletResponse response) throws IOException {
        ObjectMapper om = ObjectMapperUtil.createObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        tireService.saveTireImage(tireId, imageFile);
        //타이어 사진 저장

        TireResponseDto.TireBrief tireBrief = tireWearInspectService.inspectOneTireWear(tireId);
        // AI 모듈로 전숭 후 결과 반환

        Message message = makeMessage(Message.builder()
                .data(tireBrief), HttpStatus.OK, Constant.SUCCESS);
        om.writeValue(response.getOutputStream(), message);
    }


    @PostMapping("/{tireId}/image2") //원래 로직
    public void saveTireImage(@RequestParam("image") MultipartFile imageFile, @PathVariable Long tireId, HttpServletResponse response) throws IOException {
        ObjectMapper om = ObjectMapperUtil.createObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        tireService.saveTireImage(tireId, imageFile);
        Message message = makeMessage(Message.builder()
                .data(null), HttpStatus.OK, Constant.SUCCESS);

        om.writeValue(response.getOutputStream(), message);
    }

    @GetMapping("/upload-form")
    public ModelAndView sendImageAddForm() {
        ModelAndView modelAndView = new ModelAndView("upload-form");
        return modelAndView;
    }

    @GetMapping("/{tireId}")
    public void searchByTireId(@PathVariable("tireId") Long tireId, HttpServletResponse response) throws Exception {
        ObjectMapper om = ObjectMapperUtil.createObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        TireResponseDto result = tireService.searchByTireId(tireId);
        Message message = makeMessage(Message.builder()
                .data(result), HttpStatus.OK, Constant.SUCCESS);

        om.writeValue(response.getOutputStream(), message);
    }

    @GetMapping("/{tireId}/wear-inspections")
    public void inspectOneTireWear(@PathVariable("tireId") Long tireId, HttpServletResponse response) throws IOException {
        ObjectMapper om = ObjectMapperUtil.createObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        TireResponseDto.TireBrief tireBrief = tireWearInspectService.inspectOneTireWear(tireId);
        Message message = makeMessage(Message.builder()
                .data(tireBrief), HttpStatus.OK, Constant.SUCCESS);
        om.writeValue(response.getOutputStream(), message);
    }

    /**
     *아래의 함수가 새롭게 만들어진 함수이다.
     */
    @GetMapping("/car/{carId}/wear-inspections")
    public void inspectCarTiresWear(@PathVariable("carId") Long carId, HttpServletResponse response) throws IOException{
        ObjectMapper om = ObjectMapperUtil.createObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        List<TireResponseDto.TireBrief> tireBriefs = tireWearInspectService.inspectCarTiresWear(carId);
        Message message = makeMessage(Message.builder()
                .data(tireBriefs), HttpStatus.OK, Constant.SUCCESS);
        om.writeValue(response.getOutputStream(), message);
    }
}
