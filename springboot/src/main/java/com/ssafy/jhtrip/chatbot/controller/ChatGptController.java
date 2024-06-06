package com.ssafy.jhtrip.chatbot.controller;

import com.ssafy.jhtrip.chatbot.dto.Qustionkeyword;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.jhtrip.chatbot.dto.ChatGptResponseDto;
import com.ssafy.jhtrip.chatbot.dto.QuestionRequestDto;
import com.ssafy.jhtrip.chatbot.service.ChatGptService;

@RestController
@RequestMapping("/chat-gpt")
public class ChatGptController {

    private final ChatGptService chatGptService;

    public ChatGptController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @PostMapping("/question")
    public ChatGptResponseDto sendQuestion(@RequestBody QuestionRequestDto requestDto) {
        return chatGptService.askQuestion(requestDto);
    }

    @PostMapping("/planai")
    public ResponseEntity<?> getPlanai(@RequestBody Qustionkeyword qustionkeyword) {
        ChatGptResponseDto chatGptResponseDto = chatGptService.getAttraction(qustionkeyword);
        return ResponseEntity.ok(chatGptResponseDto);
    }
}
