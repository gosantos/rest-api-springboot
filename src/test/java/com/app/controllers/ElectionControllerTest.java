package com.app.controllers;

import com.app.models.Election;
import com.app.models.ElectionRequest;
import com.app.models.Subject;
import com.app.repositories.ElectionRepository;
import com.app.repositories.SubjectRepository;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class ElectionControllerTest {

    private ElectionController electionController;

    @Mock
    private ElectionRepository electionRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Before
    public void setUp() {
        initMocks(this);

        electionController = new ElectionController(electionRepository, subjectRepository);
    }

    @Test
    @SneakyThrows
    public void shouldStartASessionWithDefaultTime() {
        final Subject subject = Subject.builder().title("FOO BAR").build();
        given(subjectRepository.findByTitle("FOO BAR")).willReturn(Optional.of(subject));

        final Election election = Election.builder().duration(60).subject(subject).build();
        given(electionRepository.save(election)).willReturn(election);

        final ElectionRequest electionRequest = ElectionRequest.builder().subjectTitle("FOO BAR").build();
        final ResponseEntity<Election> electionResponseEntity = electionController.create(electionRequest);

        assertThat(electionResponseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(electionResponseEntity.getBody(), is(election));
    }

    @Test
    @SneakyThrows
    public void shouldStartASessionWithTimePassedByParameter() {
        final Subject subject = Subject.builder().title("FOO BAR").build();
        given(subjectRepository.findByTitle("FOO BAR")).willReturn(Optional.of(subject));

        final Election election = Election.builder().duration(120).subject(subject).build();
        given(electionRepository.save(election)).willReturn(election);

        final ElectionRequest electionRequest = ElectionRequest.builder().duration(120).subjectTitle("FOO BAR").build();
        final ResponseEntity<Election> electionResponseEntity = electionController.create(electionRequest);

        assertThat(electionResponseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(electionResponseEntity.getBody(), is(election));
    }

    @Test(expected = MockitoException.class)
    @SneakyThrows
    public void shouldReturnAnExceptionWhenASubjectIsntCreatedYet() {
        final ElectionRequest electionRequest = ElectionRequest.builder().subjectTitle("FOO BAR").build();

        given(subjectRepository.findByTitle("FOO BAR")).willThrow(NotFoundException.class);

        electionController.create(electionRequest);
    }
}