package com.hellorin.stickyMoss.documents.services;

import com.hellorin.stickyMoss.documents.domain.CV;
import com.hellorin.stickyMoss.documents.exceptions.DocumentNotFoundException;
import com.hellorin.stickyMoss.documents.repositories.CVRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by hellorin on 04.07.17.
 */
@Service
@Transactional
public class CVDocumentService implements AbstractDocumentService<CV> {
    @Autowired
    private CVRepository cvRepository;

    @Override
    public Class<?> getType() {
        return CV.class;
    }

    @Override
    public CV create(final CV document) {
        return cvRepository.save(document);
    }

    @Override
    public CV modify(final CV document) {
        if (cvRepository.exists(document.getId())) {
            CV cv = cvRepository.getOne(document.getId());

            cv.modifyWith(document);

            return cv;
        } else {
            throw new DocumentNotFoundException();
        }
    }

    @Override
    public final CV get(final Long id) {
        if (cvRepository.exists(id)) {
            return cvRepository.getOne(id);
        } else {
            throw new DocumentNotFoundException();
        }
    }
}
