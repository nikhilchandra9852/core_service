package com.akp.core_service.service;


import com.akp.core_service.model.ChatHistory;
import com.akp.core_service.model.MemoryVector;
import com.akp.core_service.repository.ChatHistoryRepository;
import com.akp.core_service.repository.MemoryVectorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatHistoryService {

    private final ChatHistoryRepository chatHistoryRepository;

    private final MemoryVectorRepository memoryVectorRepository;

    private final EmbeddingModel embeddingModel;



    @Transactional
    public void updateMemoryContent(Optional<ChatHistory> chatHistoryOptional,String content) {

        chatHistoryOptional.ifPresent(chatHistory -> {


            // get embedding of the question and answer.

            String embeddedContent = Arrays.toString(embeddingModel.embed(content));

            memoryVectorRepository.saveMemoryContent(chatHistory.getId(),content,embeddedContent);




        });
    }

    @Transactional
    public ChatHistory saveChatHistory(ChatHistory chatHistory) {
        return chatHistoryRepository.save(chatHistory);
    }
}
