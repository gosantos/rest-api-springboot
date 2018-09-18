package com.app.controllers;

import com.app.models.Associate;
import com.app.repositories.AssociateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssociatesController {

    private final AssociateRepository associateRepository;

    @Autowired
    public AssociatesController(AssociateRepository associateRepository) {
        this.associateRepository = associateRepository;
    }

    @GetMapping("/associates/{id}")
    public Associate associates(@PathVariable final Long id) throws NotFoundException {
        return associateRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }
}

