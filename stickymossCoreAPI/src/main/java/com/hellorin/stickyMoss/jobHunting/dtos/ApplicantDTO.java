package com.hellorin.stickyMoss.jobHunting.dtos;

import com.hellorin.stickyMoss.StickyMossDTO;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by hellorin on 04.07.17.
 */
@NoArgsConstructor
@Setter
@Getter
public class ApplicantDTO extends StickyMossDTO {

    private Long id;

    private String lastname;

    private String firstname;

    private String email;

    private String plainPassword;
}
