package com.hellorin.stickyMoss.documents.dtos;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import java.util.Date;

/**
 * Created by hellorin on 04.08.17.
 */
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CVDTO.class, name = "cv"),
})
public abstract class DocumentDTO {
    private Long id;

    private String name;

    private DocumentFileFormatDTO format;

    private byte[] content;

    private Date dateUploaded;
}
