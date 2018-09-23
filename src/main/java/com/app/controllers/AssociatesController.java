package com.app.controllers;

import com.app.models.Associate;
import com.app.models.AssociateRequest;
import com.app.repositories.AssociateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class AssociatesController {

    private final AssociateRepository associateRepository;

    @Autowired
    public AssociatesController(AssociateRepository associateRepository) {
        this.associateRepository = associateRepository;
    }

    @GetMapping("/associates/{id}")
    public ResponseEntity<Associate> getAssociate(@PathVariable final Long id) {
        final Optional<Associate> optionalAssociate = associateRepository.findById(id);

        return optionalAssociate.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/associates")
    public ResponseEntity<Associate> saveAssociate(@RequestBody final AssociateRequest associateRequest) {
        final Associate associate = Associate.builder().name(associateRequest.getName()).build();
        final Associate savedAssociate = associateRepository.save(associate);

        return ResponseEntity.ok(savedAssociate);
    }
}

