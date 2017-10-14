package com.hellorin.stickyMoss.jobHunting.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hellorin.stickyMoss.documents.dtos.CVDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Created by hellorin on 01.07.17.
 */
@NoArgsConstructor
@Setter
@Getter
public class JobApplicationDTO {
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateSubmitted;

    private CVDTO cv;

    private JobApplicationStatusDTO status;
}
