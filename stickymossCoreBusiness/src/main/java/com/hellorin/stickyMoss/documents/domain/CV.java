package com.hellorin.stickyMoss.documents.domain;

import com.hellorin.stickyMoss.classification.domain.Tag;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hellorin on 23.06.17.
 */
@Entity
@DiscriminatorValue(value = "CV")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CV extends Document {
    @Getter
    @Setter
    @Transient
    //@ManyToMany
    private Set<Tag> tags;

    public CV(@NonNull final String name,
              @NonNull final DocumentFileFormat format,
              @NonNull final byte[] content) {
        this(name, format, content, new HashSet<>(0));
    }

    public CV(@NonNull final String name,
              @NonNull final DocumentFileFormat format,
              @NonNull final byte[] content,
              @NonNull final Set<Tag> tags) {
        super(name, format, content);

        this.tags = tags;
    }

    public void accept(DocumentVisitor visitor) {
        visitor.visit(this);
    }
}
