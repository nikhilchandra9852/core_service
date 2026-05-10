package com.akp.core_service.controller;

import com.akp.core_service.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService service;

    @GetMapping("/ask")
    public String ask(@RequestParam String q) {
        return service.ask(q);
    }
}