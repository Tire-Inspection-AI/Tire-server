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

import static hello.capstone.domain.Message.makeMessage;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/tires")
public class TireController {

    private final TireService tireService;

    private final TireWearInspectService tireWearInspectService;

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

    @PostMapping("/{tireId}/image")
    public void saveTireImage(@RequestParam("image") MultipartFile imageFile, @PathVariable Long tireId, HttpServletResponse response) throws IOException {
        ObjectMapper om = ObjectMapperUtil.createObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        tireService.saveTireImage(tireId, imageFile);
        Message message = makeMessage(Message.builder()
                .data(null), HttpStatus.OK, Constant.SUCCESS);

        om.writeValue(response.getOutputStream(), message);
    }

    @GetMapping("/{tireId}/wear-inspections")
    public void inspectTireWear(@PathVariable("tireId") Long tireId, HttpServletResponse response) throws IOException {
        ObjectMapper om = ObjectMapperUtil.createObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        tireWearInspectService.inspectTireWear(tireId);
        Message message = makeMessage(Message.builder()
                .data(null), HttpStatus.OK, Constant.SUCCESS);
        om.writeValue(response.getOutputStream(), message);
    }
}
