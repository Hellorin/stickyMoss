package com.hellorin.stickyMoss.user.domain;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by hellorin on 21.10.17.
 */
@Entity
@Table(name = "USERS")
@Inheritance
@DiscriminatorColumn(name="user_type")
@DiscriminatorValue("user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"version", "id", "roles"})
public class ApplicationUser implements UserDetails {
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
    @Email
    private String email;

    @NotNull
    @Column(name = "is_enable")
    private Boolean enable = true;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USERS_TO_ROLES",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName="id") },
            inverseJoinColumns = { @JoinColumn(name = "role_name", referencedColumnName="role_name") }
    )
    private Set<Role> roles = new HashSet<>(0);

    public ApplicationUser(final String firstname, final String lastname, final String encPassword, final String email) {
        this.firstname = firstname.toLowerCase();
        this.lastname = lastname.toLowerCase();
        this.encPassword = encPassword;
        this.email = email;
        this.roles = Arrays.asList(Role.USER).stream()
                .map(Role::new)
                .collect(Collectors.toSet());
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

    private String uppercaseFirstLetter(final String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());
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
        return enable;
    }

    public void enableUser() {
        this.enable = true;
    }

    public void disableUser() {
        this.enable = false;
    }

    public boolean hasRole(final Predicate<String> roleFilter) {
        return this.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(roleFilter);
    }

    public void promote() {
        this.roles.clear();
        this.roles.add(new Role(Role.ADMIN));
    }

    public void demote() {
        this.roles.clear();
        this.roles.add(new Role(Role.USER));
    }
}
