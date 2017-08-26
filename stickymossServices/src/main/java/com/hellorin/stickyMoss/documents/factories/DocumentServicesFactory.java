package com.hellorin.stickyMoss.documents.factories;

import com.hellorin.stickyMoss.documents.exceptions.UnsupportedFileFormatException;
import com.hellorin.stickyMoss.documents.services.AbstractDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.Arrays;
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

    public AbstractDocumentService getServiceByDocumentType(@Valid final String documentType) {
        return getServiceByDocumentType(documentType, false);
    }

    public AbstractDocumentService getServiceByDocumentType(final @Valid String documentType, final boolean isDTO) {
        Optional<AbstractDocumentService> documentService = Arrays.asList(documentServices).stream()
                .filter(service -> {
                    String serviceDocumentType = documentType;
                    if (isDTO) {
                        serviceDocumentType = serviceDocumentType.substring(0, serviceDocumentType.length()-3);
                    }

                    return serviceDocumentType.equals(service.getType().getSimpleName());
                }).findFirst();

        if (documentService.isPresent()) {
            return documentService.get();
        } else {
            throw new UnsupportedFileFormatException();
        }
    }

}
