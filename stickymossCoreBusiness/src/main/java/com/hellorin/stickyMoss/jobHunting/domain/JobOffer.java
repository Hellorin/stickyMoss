package com.hellorin.stickyMoss.jobHunting.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * Created by hellorin on 21.06.17.
 */
@Entity
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"version", "id"})
public class JobOffer {

    @Version
    private Long version;

    @Id
    @GeneratedValue
    @Getter
    private Long id;
}
