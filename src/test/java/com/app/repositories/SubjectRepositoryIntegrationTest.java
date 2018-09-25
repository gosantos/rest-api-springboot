package com.app.repositories;

import com.app.models.Subject;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SubjectRepositoryIntegrationTest {

    @Autowired
    private SubjectRepository subjectRepository;

    @After
    public void tearDown() {
        subjectRepository.deleteAll();
    }

    @Test
    public void shouldSaveAndFetchAnSubject() {
        final Subject subject = Subject.builder().name("Foo Bar Baz").build();
        subjectRepository.save(subject);

        final Optional<Subject> subjectFoundById = subjectRepository.findById(subject.getId());

        assertThat(subjectFoundById, is(Optional.of(subject)));
    }
}