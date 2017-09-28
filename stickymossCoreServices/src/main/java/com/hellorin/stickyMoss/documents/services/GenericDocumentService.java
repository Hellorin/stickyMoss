package com.hellorin.stickyMoss.documents.services;

import com.hellorin.stickyMoss.documents.DocumentsFactory;
import com.hellorin.stickyMoss.documents.domain.Document;
import com.hellorin.stickyMoss.documents.factories.DocumentServicesFactory;
import com.hellorin.stickyMoss.documents.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by hellorin on 26.09.17.
 */
@Service
@Transactional
public class GenericDocumentService implements IGenericDocumentService {
    @Autowired
    private DocumentServicesFactory documentServicesFactory;

    @Autowired
    private DocumentsFactory documentsFactory;

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public void createDocument(final MultipartFile file, final String type) throws IOException {
        AbstractDocumentService service = documentServicesFactory.getServiceByDocumentType(type);

        Document document = documentsFactory.buildDocumentFromMultipartFile(file, type);

        service.create(document);
    }

    public Document getDocument(final Long id) {
        if (documentRepository.exists(id)) {
            return documentServicesFactory.getAllSpecificDocumentServices().stream()
                    .map(service -> service.get(id))
                    .findFirst().get();
        }
        return null;
    }

    @Override
    public void delete(final Long id) {
        if (documentRepository.exists(id)) {
            documentRepository.delete(id);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
