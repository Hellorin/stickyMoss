package com.hellorin.stickyMoss.jobHunting.dtos;

import com.hellorin.stickyMoss.StickyMossDTO;
import com.hellorin.stickyMoss.documents.dtos.CVDTO;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by hellorin on 01.07.17.
 */
@NoArgsConstructor
@Setter
@Getter
public class JobApplicationDTO extends StickyMossDTO {
    private Long id;

    private Date dateSubmitted;

    private CVDTO cv;

    private JobApplicationStatusDTO status;
}
