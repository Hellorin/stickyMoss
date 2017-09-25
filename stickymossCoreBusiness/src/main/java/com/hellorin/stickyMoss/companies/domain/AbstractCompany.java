package com.hellorin.stickyMoss.companies.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by hellorin on 21.06.17.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"version", "id"})
@Entity(name = "ABSTRACT_COMPANIES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "companyType")
public abstract class AbstractCompany {
    @Version
    private Long version;

    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @NotNull
    @Getter
    private String name;

    @NotNull
    @Getter
    @Setter
    private CompanySize companySize;


    public AbstractCompany(@NonNull final String name, @NonNull final CompanySize companySize) {
        if (!name.isEmpty()) {
            this.name = name;
            this.companySize = companySize;
        } else {
            throw new IllegalArgumentException("Company's name cannot be empty");
        }
    }
}
