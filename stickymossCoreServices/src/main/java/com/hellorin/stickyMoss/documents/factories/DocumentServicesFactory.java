package com.hellorin.stickyMoss.documents.factories;

import com.hellorin.stickyMoss.documents.exceptions.UnsupportedFileFormatException;
import com.hellorin.stickyMoss.documents.services.AbstractDocumentService;
import com.hellorin.stickyMoss.documents.services.IGenericDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * Created by hellorin on 04.07.17.
 */
@Component
@Validated
public class DocumentServicesFactory {
    static Logger logger = Logger.getLogger(DocumentServicesFactory.class.getName());

    @Autowired
    private AbstractDocumentService[] documentServices;

    @PostConstruct
    public void init() {
        String documentsServiceListStr = Arrays.asList(documentServices)
                .stream()
                .map(AbstractDocumentService::getType)
                .map(type -> String.format("%s", type))
                .collect(Collectors.joining(", "));

        logger.info("Loaded the following documentServices: (" + documentsServiceListStr + ")");
    }

    public AbstractDocumentService getServiceByDocumentType(final @Valid String documentType) {
        Optional<AbstractDocumentService> documentService = Arrays.asList(documentServices).stream()
                .filter(service -> documentType.equals(service.getType().getSimpleName()))
                .findFirst();

        return documentService.orElseThrow(() -> new UnsupportedFileFormatException());
    }

    public List<AbstractDocumentService> getAllSpecificDocumentServices() {
        return Arrays.asList(documentServices);
    }
}
