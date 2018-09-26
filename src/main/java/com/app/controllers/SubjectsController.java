package com.app.controllers;

import com.app.models.Subject;
import com.app.models.SubjectRequest;
import com.app.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubjectsController {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectsController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @PostMapping(value = "subjects")
    public ResponseEntity<Subject> postASubject(final SubjectRequest subjectRequest) {
        final Subject subject = Subject.builder().title(subjectRequest.getTitle()).build();
        final Subject savedSubject = subjectRepository.save(subject);

        return ResponseEntity.ok(savedSubject);
    }
}
