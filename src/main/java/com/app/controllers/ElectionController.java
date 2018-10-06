package com.app.controllers;

import com.app.models.Election;
import com.app.models.ElectionRequest;
import com.app.models.Subject;
import com.app.repositories.ElectionRepository;
import com.app.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ElectionController {

    private final ElectionRepository electionRepository;

    private final SubjectRepository subjectRepository;

    @Autowired
    public ElectionController(ElectionRepository electionRepository, SubjectRepository subjectRepository) {
        this.electionRepository = electionRepository;
        this.subjectRepository = subjectRepository;
    }

    @PostMapping(value = "/elections/")
    public ResponseEntity<Election> create(@RequestBody final ElectionRequest electionRequest) throws NotFoundException {
        final String subjectTitle = electionRequest.getSubjectTitle();
        final Optional<Subject> optionalSubject = subjectRepository.findByTitle(subjectTitle);
        final Subject subject = optionalSubject.orElseThrow(NotFoundException::new);

        final Integer duration = electionRequest.getDuration();
        final Election election = Election.builder().duration(duration).subject(subject).build();
        electionRepository.save(election);

        return ResponseEntity.ok(election);
    }
}
