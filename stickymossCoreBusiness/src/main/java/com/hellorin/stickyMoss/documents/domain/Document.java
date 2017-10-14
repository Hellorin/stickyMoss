package com.hellorin.stickyMoss.documents.domain;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created by hellorin on 23.06.17.
 */
@Entity(name = "Documents")
@Table(name = "Documents")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "docType")
@EqualsAndHashCode(exclude = {"version", "id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Getter
    @Setter
    @NotNull
    private LocalDate dateUploaded;

    public Document(@NonNull @NotEmpty final String name,
                    @NonNull final DocumentFileFormat format,
                    @NonNull final byte[] content) {
        if (content.length > 0) {
            this.name = name;
            this.format = format;
            this.content = content;
            this.dateUploaded = LocalDate.now();
        } else {
            throw new IllegalArgumentException("Content of the file cannot be empty");
        }
    }

    public void modifyWith(Document doc) {
        setName(doc.getName());
        setContent(doc.getContent());
        setFormat(doc.getFormat());
    }
}
