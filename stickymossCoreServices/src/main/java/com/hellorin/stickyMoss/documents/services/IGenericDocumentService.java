package com.hellorin.stickyMoss.documents.services;

import com.hellorin.stickyMoss.documents.domain.Document;
import com.hellorin.stickyMoss.documents.exceptions.DocumentNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by hellorin on 26.09.17.
 */
@Validated
public interface IGenericDocumentService {
    void createDocument(MultipartFile file, @Valid String type) throws IOException;

    <T extends Document> T getDocument(@Valid Long id) throws DocumentNotFoundException;

    <T extends Document> T modifyDocument(@Valid Document document) throws DocumentNotFoundException;

    void delete(@Valid Long id) throws DocumentNotFoundException;
}
