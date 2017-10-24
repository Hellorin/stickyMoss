package com.hellorin.stickyMoss.user.domain;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by hellorin on 22.10.17.
 */
@Entity
@Table(name = "USER_ROLES")
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    public static SimpleGrantedAuthority USER = new SimpleGrantedAuthority("USER");

    public static SimpleGrantedAuthority ADMIN = new SimpleGrantedAuthority("ADMIN");

    @Id
    @Column(name = "role_name")
    @NonNull
    @Type(type = "com.hellorin.stickyMoss.user.usertypes.SimpleGrantedAuthorityType")
    private SimpleGrantedAuthority roleName;

}
