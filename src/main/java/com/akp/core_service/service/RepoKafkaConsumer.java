

package com.akp.core_service.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import com.akp.core_service.model.RepoFileEvent;

import java.io.DataInput;
import java.io.IOException;


@Service
@RequiredArgsConstructor
public class RepoKafkaConsumer {

    private final DocumentService documentService;

    private final ObjectMapper objectMapper;



    @KafkaListener(topics="repo-files-1",groupId = "core_service_consumer")
    public void consume(@Header(KafkaHeaders.RECEIVED_KEY) String key, String event) throws JsonProcessingException {

        System.out.println("Received message: " + key );

        RepoFileEvent fileEvent =
                objectMapper.readValue(event, RepoFileEvent.class);

        //processing the document
        documentService.save(fileEvent.getFileContent(),fileEvent.getFileName(),"TEXT");

    }



}