package com.app.repositories;

import com.app.models.Election;
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
public class ElectionRepositoryIntegrationTest {

    @Autowired
    private ElectionRepository electionRepository;

    @After
    public void tearDown() {
        electionRepository.deleteAll();
    }

    @Test
    public void shouldBeAbleToSaveAndFetchAnElection() {
        final Election election = Election.builder().build();
        electionRepository.save(election);

        final Optional<Election> electionFoundById = electionRepository.findById(election.getId());

        assertThat(electionFoundById, is(Optional.of(election)));
    }

    @Test
    public void shouldBeAbleToFetchAnElectionWithItsRelation() {
        final Subject subject = Subject.builder().title("FOO BAR").build();
        final Election election = Election.builder().subject(subject).build();

        electionRepository.save(election);

        final Optional<Election> electionFoundById = electionRepository.findById(election.getId());
        final Subject actual = electionFoundById.get().getSubject();

        assertThat(actual, is(subject));
        assertThat(actual.getTitle(), is("FOO BAR"));
    }
}