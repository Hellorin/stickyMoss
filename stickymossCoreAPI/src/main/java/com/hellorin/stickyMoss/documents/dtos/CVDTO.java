package com.hellorin.stickyMoss.documents.dtos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hellorin on 01.07.17.
 */
@Data
@NoArgsConstructor
public class CVDTO extends DocumentDTO {

    private Set<String> tags = new HashSet<>(1);
}
