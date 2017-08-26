package com.hellorin.stickyMoss.translation.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hellorin on 09.07.17.
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Translation {
    @Version
    private Long version;

    @Id
    @Getter
    private Long id;

    @NonNull
    @Getter
    private String key;

    @Getter
    @Setter
    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="translation_attributes", joinColumns=@JoinColumn(name="translation_id"))
    @NonNull
    private Map<String, String> translationsByLanguages = new HashMap<String, String>();

    public Translation(final String key, final Map<String, String> translationsByLanguages) {
        this.key = key;
        this.translationsByLanguages = translationsByLanguages;
    }
}
