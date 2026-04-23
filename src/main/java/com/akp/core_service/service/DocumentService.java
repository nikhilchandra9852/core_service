package com.akp.core_service.service;


import com.akp.core_service.model.Document;
import com.akp.core_service.repository.DocumentRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private DocumentRepository documentRepository;


    public List<Document> findAll() {
        return documentRepository.findAll();
    }


}
