package com.akp.core_service.repository;

import com.akp.core_service.model.ChatHistory;
import com.akp.core_service.model.MemoryVector;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MemoryVectorRepository extends JpaRepository<MemoryVector,Long> {


    @Transactional
    @Modifying
    @Query(value = """
            insert into memory_vector(content,chat_history_id,embedding,timeStamp)
            values 
            (:content,:chatHistoryId,CAST(:embeddedContent AS vector),NOW())

""", nativeQuery = true)
    void saveMemoryContent(
            @Param("chatHistoryId") Long chatHistoryId,
            @Param("content") String content,
            @Param("embeddedContent") String embeddedContent);
}
