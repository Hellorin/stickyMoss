package com.hellorin.stickyMoss.jobHunting.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by hellorin on 04.07.17.
 */
@NoArgsConstructor
@Setter
@Getter
public class ApplicantDTO {

    private Long id;

    private String lastname;

    private String firstname;

    private String email;

    private String pwd;
}
