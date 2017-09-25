package com.hellorin.stickyMoss.documents.domain;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by hellorin on 23.06.17.
 */
@EqualsAndHashCode(exclude = {"version", "id"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@MappedSuperclass
public abstract class Document {
    @Version
    private Long version;

    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String name;

    @Getter
    @Setter
    @NotNull
    private DocumentFileFormat format;

    @Lob
    @Getter
    @Setter
    @NotNull
    private byte[] content;

    @Temporal(TemporalType.DATE)
    @Getter
    @Setter
    @NotNull
    private Date dateUploaded;

    public Document(@NonNull @NotEmpty final String name,
                    @NonNull final DocumentFileFormat format,
                    @NonNull final byte[] content) {
        if (content.length > 0) {
            this.name = name;
            this.format = format;
            this.content = content;
            this.dateUploaded = new Date();
        } else {
            throw new IllegalArgumentException("Content of the file cannot be empty");
        }
    }
}
