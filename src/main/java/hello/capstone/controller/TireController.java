package hello.capstone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hello.capstone.domain.Message;
import hello.capstone.dto.response.car.CarResponseDto;
import hello.capstone.dto.response.tire.response.TireResponseDto;
import hello.capstone.service.TireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/tires")
public class TireController {

    private final TireService tireService;

    @GetMapping("/upload-form")
    public ModelAndView sendImageAddForm() {
        ModelAndView modelAndView = new ModelAndView("upload-form");
        return modelAndView;
    }

    @GetMapping("/{tireId}")
    public void searchByTireId(@PathVariable("tireId") Long tireId, HttpServletResponse response) throws Exception {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        TireResponseDto result = tireService.searchByTireId(tireId);
        Message message = Message.builder()
                .data(result)
                .status(HttpStatus.OK)
                .message("success")
                .build();

        om.writeValue(response.getOutputStream(), message);
    }

    @PostMapping("/{tireId}/image")
    public void saveTireImage(@RequestParam("image") MultipartFile imageFile, @PathVariable Long tireId, HttpServletResponse response) throws IOException {

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        tireService.saveTireImage(tireId, imageFile);
        Message message = Message.builder()
                .status(HttpStatus.OK)
                .message("success")
                .build();

        om.writeValue(response.getOutputStream(), message);
    }
}
