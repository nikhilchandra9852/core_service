package com.akp.core_service.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RepoFileEvent {

    private String id;

    private String repoUrl;

    private String fileName;

    private String fileContent;

    private String timestamp;
}
