package com.hellorin.stickyMoss.classification.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Created by hellorin on 23.06.17.
 */
@Entity
@Table(name = "TAGS")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = {"version", "id"})
public class Tag {
    @Version
    private Long version;

    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @Getter
    @NonNull
    private String name;

    public Tag(@NonNull final String name) {
        if (!name.isEmpty()) {
            this.name = name;
        } else {
            this.name = "error";
            throw new IllegalArgumentException("Tag name cannot be empty");
        }
    }

}
