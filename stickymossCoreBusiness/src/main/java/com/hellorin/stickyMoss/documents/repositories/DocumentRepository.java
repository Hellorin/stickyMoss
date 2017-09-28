package com.hellorin.stickyMoss.documents.repositories;

import com.hellorin.stickyMoss.documents.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by hellorin on 26.09.17.
 */
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Documents d WHERE d.id = :id")
    boolean documentExists(@Param("id") Long id);

    @Query("DELETE FROM Documents d WHERE d.id = :id")
    void deleteDocument(@Param("id") Long id);
}
