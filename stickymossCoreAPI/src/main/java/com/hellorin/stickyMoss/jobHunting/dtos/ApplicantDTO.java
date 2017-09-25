package com.hellorin.stickyMoss.jobHunting.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by hellorin on 04.07.17.
 */
@Data
@NoArgsConstructor
public class ApplicantDTO {

    private Long id;

    private String lastname;

    private String firstname;

    private String email;

    private String plainPassword;
}
