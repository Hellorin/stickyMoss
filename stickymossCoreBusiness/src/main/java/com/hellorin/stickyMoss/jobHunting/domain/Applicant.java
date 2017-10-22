package com.hellorin.stickyMoss.jobHunting.domain;

import com.hellorin.stickyMoss.user.domain.ApplicationUser;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by hellorin on 23.06.17.
 */
@Entity
@DiscriminatorValue("applicant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Applicant extends ApplicationUser {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @BatchSize(size = 5)
    private List<JobApplication> jobApplications = new ArrayList<>(0);

    public Applicant(final String firstname, final String lastname, final String encPassword, final String email) {
        super(firstname, lastname, encPassword, email);
    }

    public void addJobApplication(final JobApplication jobApplication) {
        jobApplications.add(jobApplication);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        int jobApplicationsHash = Objects.hash(jobApplications.stream()
                .map(Object::hashCode)
                .collect(Collectors.toList())
                .toArray());
        return super.hashCode() + 89 * jobApplicationsHash;
    }
}
