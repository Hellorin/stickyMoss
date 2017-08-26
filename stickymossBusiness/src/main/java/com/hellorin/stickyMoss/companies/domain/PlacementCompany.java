package com.hellorin.stickyMoss.companies.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by hellorin on 21.06.17.
 */
@Entity
@Table(name = "PLACEMENT_COMPANIES")
public class PlacementCompany extends AbstractCompany {
    public PlacementCompany(@NonNull final String name, final CompanySize companySize) {
        super(name, companySize);
    }
}
