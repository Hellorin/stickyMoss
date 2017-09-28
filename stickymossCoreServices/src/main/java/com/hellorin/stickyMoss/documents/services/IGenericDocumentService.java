package com.hellorin.stickyMoss.documents.services;

import com.hellorin.stickyMoss.documents.domain.Document;
import org.springframework.data.util.Pair;
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

    <T extends Document> T getDocument(@Valid Long id);

    void delete(@Valid Long id);
}
