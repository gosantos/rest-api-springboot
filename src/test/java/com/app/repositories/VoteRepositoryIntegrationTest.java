package com.app.repositories;

import com.app.models.Vote;
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
public class VoteRepositoryIntegrationTest {

    @Autowired
    private VoteRepository voteRepository;
    
    @After
    public void tearDown() {
        voteRepository.deleteAll();
    }

    @Test
    public void shouldBeAbleToSaveAndFetchAVote() {
        final Vote vote = Vote.builder().associateId(123L).subjectId(231L).value(true).build();

        voteRepository.save(vote);

        final Optional<Vote> voteFoundById = voteRepository.findById(vote.getId());

        assertThat(voteFoundById, is(Optional.of(vote)));
    }
}