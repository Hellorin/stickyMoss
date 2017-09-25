package com.hellorin.stickyMoss.companies.domain;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by hellorin on 21.06.17.
 */
@Entity
@Table(name = "SERVICES_COMPANIES")
@DiscriminatorValue(value = "Services")
public class ServicesCompany extends AbstractCompany {

    public ServicesCompany(@NonNull final String name, final CompanySize companySize) {
        super(name, companySize);
    }
}
