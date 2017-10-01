package com.hellorin.stickyMoss.documents;

import com.hellorin.stickyMoss.documents.domain.CV;
import com.hellorin.stickyMoss.documents.domain.Document;
import com.hellorin.stickyMoss.documents.domain.DocumentFileFormat;
import org.apache.tika.Tika;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by hellorin on 26.09.17.
 */
@Component
public class DocumentsFactory {
    private Tika tika = new Tika();

    private DocumentFileFormat extractFileFormat(final MultipartFile file) throws IOException {
        String detectedType = tika.detect(file.getBytes());

        switch (detectedType) {
            case MediaType.APPLICATION_PDF_VALUE:
                return DocumentFileFormat.PDF;
            default:
                throw new IllegalArgumentException();
        }

    }

    public Document buildDocumentFromMultipartFile(final MultipartFile file, final String type) throws IOException {
        DocumentFileFormat format = extractFileFormat(file);

        switch(type) {
            case "CV":
                return new CV(file.getName(), format, file.getBytes());
            default:
                throw new IllegalArgumentException();
        }

    }
}
