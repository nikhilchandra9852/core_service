package com.akp.core_service.controller;

import com.akp.core_service.model.Document;
import com.akp.core_service.model.Types;
import com.akp.core_service.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    private final EmbeddingModel embeddingModel;

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        return ResponseEntity.ok(documentService.findAll());
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<String> saveDocument(@RequestParam("file") MultipartFile file,
                                               @RequestParam("type") String type) throws java.io.IOException {

        System.out.println(type);

        if (Objects.equals(type, Types.PDF.toString())) {
            try (PDDocument document =
                         PDDocument.load(file.getInputStream())) {

                PDFTextStripper stripper = new PDFTextStripper();

                String text = stripper.getText(document);
                String name = file.getOriginalFilename();


                return ResponseEntity.ok(documentService.save(text, name, type));

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().body(e.getMessage());
            }
        }else if (Objects.equals(type, Types.TEXT.toString())) {
            String name = file.getOriginalFilename();
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            return ResponseEntity.ok(documentService.save(content, name, type));
        }else if(Objects.equals(type, Types.EXCEL.toString())) {

            return ResponseEntity.ok("Excel need to handle yet");
        }

        return ResponseEntity.internalServerError().body("Invalid type");
    }


    @GetMapping("/askQuestion")
    public ResponseEntity<String> askQuestion(@RequestParam("question")  String question) {
        return ResponseEntity.ok(documentService.askQuestion(question));
    }

    @GetMapping("/test")
    public String test() {

        float[] embedding =
                embeddingModel.embed("hello world");

        return "VECTOR SIZE : " + embedding.length;
    }

}
