package com.controller;

import com.dto.request.AIRequest;
import com.service.AIService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@Slf4j
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AIController {
    AIService aiService;

    @PostMapping("/analyze-tech")
    String chat(@RequestBody AIRequest aiRequest) throws ParseException {
        return aiService.analyzeTech(aiRequest);
    }

}
