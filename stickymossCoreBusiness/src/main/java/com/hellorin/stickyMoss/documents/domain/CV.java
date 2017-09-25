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
@Table(name = "CVS")
public class CV extends Document {
    @Getter
    @Setter
    @ManyToMany
    private Set<Tag> tags = new HashSet<>(1);

    public CV(@NonNull final String name,
              @NonNull final DocumentFileFormat format,
              @NonNull final byte[] content,
              @NonNull final Set<Tag> tags) {
        super(name, format, content);

        this.tags = tags;
    }
}
