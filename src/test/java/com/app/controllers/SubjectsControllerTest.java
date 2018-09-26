package com.app.controllers;

import com.app.models.Subject;
import com.app.models.SubjectRequest;
import com.app.repositories.SubjectRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class SubjectsControllerTest {
    private SubjectsController subjectsController;

    @Mock
    private SubjectRepository subjectRepository;

    @Before
    public void setUp() {
        initMocks(this);

        subjectsController = new SubjectsController(subjectRepository);
    }

    @Test
    public void shouldBeAbleToPostASubject() {
        final SubjectRequest subjectRequest = SubjectRequest.builder().title("FOO BAR").build();
        final ResponseEntity<Subject> subjectResponseEntity = subjectsController.postASubject(subjectRequest);

        final Subject subject = Subject.builder().id(123L).title("FOO BAR").build();
        given(subjectRepository.save(subject)).willReturn(subject);

        assertThat(subjectResponseEntity.getStatusCodeValue(), is(200));
    }
}