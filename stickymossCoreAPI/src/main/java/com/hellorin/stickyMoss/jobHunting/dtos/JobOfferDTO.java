package com.hellorin.stickyMoss.jobHunting.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Created by hellorin on 14.10.17.
 */
@NoArgsConstructor
@Getter
@Setter
public class JobOfferDTO {
    private Long id;

    private LocalDate dateDiscovered;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private JobOfferStatusDTO status;

}
