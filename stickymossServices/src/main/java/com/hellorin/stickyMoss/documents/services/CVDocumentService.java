package com.hellorin.stickyMoss.documents.services;

import com.hellorin.stickyMoss.documents.domain.CV;
import com.hellorin.stickyMoss.documents.repositories.CVRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * Created by hellorin on 04.07.17.
 */
@Service
@Transactional
@Validated
public class CVDocumentService implements AbstractDocumentService<CV> {

    @Autowired
    private CVRepository cvRepository;

    @Override
    public Class<?> getType() {
        return CV.class;
    }

    @Override
    public CV create(@Valid final CV document) {
        return cvRepository.save(document);
    }

    @Override
    public CV modify(@Valid final CV document) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(@Valid final CV document) {
        cvRepository.delete(document);
    }


}
