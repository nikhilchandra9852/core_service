package com.akp.core_service.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class MemoryVector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "chat_history_id")
    private ChatHistory chatHistory;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "vector(1024)")
    private String embedding;

    private LocalDateTime timestamp;
}
