package com.hellorin.stickyMoss.user.repositories;

import com.hellorin.stickyMoss.user.domain.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hellorin on 22.10.17.
 */
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

}
