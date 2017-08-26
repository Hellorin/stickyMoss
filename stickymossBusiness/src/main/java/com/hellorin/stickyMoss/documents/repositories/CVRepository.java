package com.hellorin.stickyMoss.documents.repositories;

import com.hellorin.stickyMoss.documents.domain.CV;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hellorin on 04.07.17.
 */
public interface CVRepository extends JpaRepository<CV, Long> {
}
