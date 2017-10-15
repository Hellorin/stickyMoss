package com.hellorin.stickyMoss.jobHunting.repositories;


import com.hellorin.stickyMoss.jobHunting.domain.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hellorin on 14.10.17.
 */
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

}
