package com.hellorin.stickyMoss.facade.documents.controllers;

import com.hellorin.stickyMoss.documents.domain.Document;
import com.hellorin.stickyMoss.documents.dtos.DocumentDTO;
import com.hellorin.stickyMoss.documents.factories.DocumentServicesFactory;
import com.hellorin.stickyMoss.documents.services.AbstractDocumentService;
import com.hellorin.stickyMoss.facade.mappers.StickyMossOrikaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hellorin on 04.08.17.
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RestController
@RequestMapping
@interface DocumentsRestFacadeController {
    @AliasFor(annotation = RequestMapping.class)
    String[] value () default {"api/stickyMoss/documents"};
}

@DocumentsRestFacadeController
public class DocumentsRestFacade {

    @Autowired
    private DocumentServicesFactory documentServicesFactory;

    @Autowired
    private StickyMossOrikaMapper stickyMossOrikaMapper;

    @PutMapping
    public ResponseEntity<DocumentDTO> addNewDocument(@RequestBody final DocumentDTO documentDTO) {
        AbstractDocumentService service = documentServicesFactory.getServiceByDocumentType(documentDTO.getClass().getSimpleName(), true);

        Document document = (Document) stickyMossOrikaMapper.getFacade().map(documentDTO, service.getType());

        Document documentCreated = service.create(document);

        return ResponseEntity.ok(stickyMossOrikaMapper.getFacade().map(documentCreated, documentDTO.getClass()));
    }

    public void removeDocument(final Long id) {
        throw new UnsupportedOperationException();
    }
}
