package com.hellorin.stickyMoss.documents.services;

import com.hellorin.stickyMoss.documents.domain.Document;

/**
 * Created by hellorin on 04.07.17.
 */
public interface AbstractDocumentService<T extends Document> {

    Class<?> getType();

    T create(T document);

    T modify(T document);

    void delete(T document);
}
