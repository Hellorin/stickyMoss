package com.hellorin.stickyMoss.companies.domain;

import lombok.NonNull;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by hellorin on 23.06.17.
 */
@Entity
@DiscriminatorValue(value = "Company")
public class Company extends AbstractCompany {

    public Company(@NonNull final String name, final CompanySize companySize) {
        super(name, companySize);
    }

}
