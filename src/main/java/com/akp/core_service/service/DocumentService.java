package com.akp.core_service.service;


import com.akp.core_service.model.Document;
import com.akp.core_service.model.Types;
import com.akp.core_service.repository.DocumentRepository;
import com.akp.core_service.utils.GeneratePromptUtil;
import com.akp.core_service.utils.TextChunker;
import com.pgvector.PGvector;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    private final EmbeddingModel embeddingModel;

    private final ChatService chatService;


    public List<Document> findAll() {
        return documentRepository.findAll();
    }


    public String save(String content,String name, String type) {

        List<String> getChunkStrings = TextChunker.chuckData(content,500,100);

        for(int i=0;i<getChunkStrings.size();i++) {

            float[] vectorContent = embeddingModel.embed(getChunkStrings.get(i));
            String vector = Arrays.toString(vectorContent);

            documentRepository.saveDocument(content, vector, name, i,Types.valueOf(type).name());
        }
        return "File is embedded and upload in the db.";
    }

    public String askQuestion(String question) {

        float[] vectorContent = embeddingModel.embed(question);
        String vector = Arrays.toString(vectorContent);
        List<Document> document = documentRepository.findSimilar(vector);

        // get the content and send to gemini

        String content = document.stream().map(Document::getContent).collect(Collectors.joining("\n\n"));

        String prompt = GeneratePromptUtil.generatePrompt(question,content);

        return chatService.ask(prompt);

    }



}
