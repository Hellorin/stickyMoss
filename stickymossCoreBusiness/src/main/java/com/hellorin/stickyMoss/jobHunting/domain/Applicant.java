package com.hellorin.stickyMoss.jobHunting.domain;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by hellorin on 23.06.17.
 */
@Entity
@Table(name = "APPLICANTS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"version", "id"})
public class Applicant implements UserDetails {
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "applicantIdGen")
    @SequenceGenerator(name = "applicantIdGen",
            sequenceName = "APPLICANT_SEQ")
    @Getter
    private Long id;

    @NonNull
    @NotNull
    @NotEmpty
    private String firstname;

    @NonNull
    @NotNull
    @NotEmpty
    private String lastname;

    @Setter
    @NonNull
    @NotNull
    @NotEmpty
    private String encPassword;

    @Getter
    @Setter
    @NonNull
    @NotNull
    @NotEmpty
    private String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    private List<JobApplication> jobApplications = new ArrayList<>(0);

    public Applicant(final String firstname, final String lastname, final String encPassword, final String email) {
        this.firstname = firstname.toLowerCase();
        this.lastname = lastname.toLowerCase();
        this.encPassword = encPassword;
        this.email = email;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname.toLowerCase();
    }

    public String getFirstname() {
        return uppercaseFirstLetter(this.firstname);
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname.toLowerCase();
    }

    public String getLastname() {
        return uppercaseFirstLetter(this.lastname);
    }

    public void addJobApplication(final JobApplication jobApplication) {
        jobApplications.add(jobApplication);
    }

    private String uppercaseFirstLetter(final String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public boolean verifyPassword(final String enteredEncPassword) {
        return encPassword.equals(enteredEncPassword);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return encPassword;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
