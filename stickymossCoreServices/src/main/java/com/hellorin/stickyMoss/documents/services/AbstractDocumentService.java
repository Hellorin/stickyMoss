package com.hellorin.stickyMoss.documents.services;

import com.hellorin.stickyMoss.documents.domain.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * Created by hellorin on 04.07.17.
 */
@Validated
public interface AbstractDocumentService<T extends Document> {

    Class<?> getType();

    T create(@Valid T document);

    T modify(@Valid T document);

    T get(@Valid Long id);

    void delete(@Valid Long id);
}
