package com.akp.core_service.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString(exclude = "memoryVector")
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chatHistory", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemoryVector> memoryVectors = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime time;
}
