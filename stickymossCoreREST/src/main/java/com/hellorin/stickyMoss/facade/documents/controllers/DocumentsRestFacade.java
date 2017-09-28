package com.hellorin.stickyMoss.facade.documents.controllers;

import com.hellorin.stickyMoss.StickyMossDTO;
import com.hellorin.stickyMoss.documents.domain.Document;
import com.hellorin.stickyMoss.documents.dtos.DocumentDTO;
import com.hellorin.stickyMoss.documents.services.IGenericDocumentService;
import com.hellorin.stickyMoss.facade.mappers.StickyMossOrikaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private IGenericDocumentService genericDocumentService;

    @Autowired
    private StickyMossOrikaMapper stickyMossOrikaMapper;

    @PostMapping
    public void uploadNewDocument(@RequestParam("file") final MultipartFile file, @RequestParam(value="type") final String type) throws IOException {
        genericDocumentService.createDocument(file, type);
    }

    @GetMapping("{id}")
    public ResponseEntity<Resource> getDocument(@PathVariable final Long id) {
        Document document = genericDocumentService.getDocument(id);

        DocumentDTO dto = (DocumentDTO) stickyMossOrikaMapper.getFacade().intelligentMappingToDTO(document);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + dto.getFilename() + "\"").body(dto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value =  HttpStatus.OK)
    public void removeDocument(@PathVariable final Long id) {
        genericDocumentService.delete(id);
    }
}
