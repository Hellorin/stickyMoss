package com.hellorin.stickyMoss.translation.repositories;

import com.hellorin.stickyMoss.translation.domain.Translation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by hellorin on 09.07.17.
 */
public interface TranslationRepository extends JpaRepository<Translation, Long> {

   Optional<Translation> findByKey(String key);

}
