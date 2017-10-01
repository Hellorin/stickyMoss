package com.hellorin.stickyMoss.documents.dtos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hellorin on 01.07.17.
 */
@NoArgsConstructor
@Setter
@Getter
public class CVDTO extends DocumentDTO {

    private Set<String> tags = new HashSet<>(1);

}
