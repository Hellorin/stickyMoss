package com.hellorin.stickyMoss.jobHunting.dtos;

import com.hellorin.stickyMoss.documents.dtos.CVDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by hellorin on 01.07.17.
 */
@Data
@NoArgsConstructor
public class JobApplicationDTO {
    private Long id;

    private Date dateSubmitted;

    private CVDTO cv;

    private JobApplicationStatusDTO status;
}
