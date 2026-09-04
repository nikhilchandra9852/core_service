package com.akp.core_service.service;



import com.akp.core_service.dtos.UserModel;
import com.akp.core_service.model.ChatHistory;
import com.akp.core_service.model.MemoryVector;
import com.akp.core_service.model.User;
import com.akp.core_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {



    private final UserRepository userRepository;


    private final ChatHistoryService chatHistoryService;

    @Async
    @Transactional
    public void updateMemoryContent(String question, String response, String email, Long chatId) {


        Optional<User> user = userRepository.findUsersByEmail(email);

        if(user.isEmpty()) {
            return;
        }

        List<ChatHistory> chatHistoryList = user.get().getChatHistory();
        System.out.println("Email" +user.get().getEmail() + "Memory is updating");
        Optional<ChatHistory> chatHistoryOptional = chatHistoryList.stream().filter(it->it.getId().equals(chatId)).findFirst();
        chatHistoryService.updateMemoryContent(chatHistoryOptional,question+response);


    }

    public List<MemoryVector> getMemoryContent(String email, Long chatId) {

        Optional<User> user = userRepository.findUsersByEmail(email);

        if(user.isEmpty()){
            return null;
        }
        List<ChatHistory> chatHistoryList = user.get().getChatHistory();

//        Optional<MemoryVector>  optionalMemoryVector = chatHistoryList.stream().filter(it->it.getId().equals(chatId)).findFirst()

        return chatHistoryList.stream().filter(it->it.getId().equals(chatId)).findFirst().orElseThrow().getMemoryVectors();
    }

    public String saveUser(UserModel userModel){

        User user = new User();
        user.setEmail(userModel.getUsername());
        user.setPassword(userModel.getPassword());
        user.setChatHistory(new ArrayList<>());

        userRepository.save(user);

        return user.getEmail();
    }

    public List<ChatHistory> getChatHistory(String email){
        return userRepository.findUsersByEmail(email).map(User::getChatHistory).orElse(new ArrayList<>());
    }

    public ChatHistory createChat(String emailId) {

        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setUser(userRepository.findUsersByEmail(emailId).orElseThrow());
        chatHistory.setTime(LocalDateTime.now());
        return chatHistoryService.saveChatHistory(chatHistory);
    }
}
