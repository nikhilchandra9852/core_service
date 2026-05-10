package com.akp.core_service.repository;

import com.akp.core_service.model.Document;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {


    @Query(value = """
        SELECT * FROM document
        ORDER BY embedding <-> CAST(:embedding AS vector)
        LIMIT 5
        """, nativeQuery = true)
    List<Document> findSimilar(@Param("embedding") String embedding);


    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO document
        (content, creation_date, embedding, name,chunk_index, type)
        VALUES
        (:content, NOW(), CAST(:embedding AS vector), :name,:chunkIndex, :type)
        """, nativeQuery = true)
    void saveDocument(
            @Param("content") String content,
            @Param("embedding") String embedding,
            @Param("name") String name,
            @Param("chunkIndex") Integer chunkIndex,
            @Param("type") String type
    );
}
