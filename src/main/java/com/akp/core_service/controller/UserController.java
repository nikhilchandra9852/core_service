package com.akp.core_service.controller;


import com.akp.core_service.dtos.UserModel;
import com.akp.core_service.model.ChatHistory;
import com.akp.core_service.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/create")
    public ResponseEntity<EntityModel<UserModel>> save(@RequestBody UserModel userModel) {

        String emailId = userService.saveUser(userModel);
        // adding hateoas

        EntityModel<UserModel> entityModel = EntityModel.of(userModel);

        entityModel.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(UserController.class).history(emailId)
                ).withRel("Chathistory")
        );

        return ResponseEntity.ok().body(entityModel);
    }

    @GetMapping("/history/emailId")
    public ResponseEntity<List<ChatHistory>> history(@RequestParam String email) {
        return ResponseEntity.ok(userService.getChatHistory(email));
    }

    @PostMapping("/chat/emailId")
    public ResponseEntity<ChatHistory> saveHistory(@RequestParam String emailId) {

        return ResponseEntity.ok().body(userService.createChat(emailId));

    }
}
