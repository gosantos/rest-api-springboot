package com.app.controllers;

import com.app.SessionExpired;
import com.app.models.Associate;
import com.app.models.Election;
import com.app.models.Vote;
import com.app.models.VoteRequest;
import com.app.models.VoteValue;
import com.app.repositories.AssociateRepository;
import com.app.repositories.ElectionRepository;
import com.app.repositories.VoteRepository;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class VotesControllerTest {

    private VotesController votesController;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private ElectionRepository electionRepository;

    @Mock
    private AssociateRepository associateRepository;

    @Before
    public void setUp() {
        initMocks(this);

        votesController = new VotesController(voteRepository, electionRepository, associateRepository);
    }

    @Test
    @SneakyThrows
    public void givenAnValidElectionAndAnExistingUserShouldBeAbleToHandleAnElection() {
        final Optional<Associate> optionalAssociate = Optional.of(Associate.builder().build());
        given(associateRepository.findById(1L)).willReturn(optionalAssociate);

        final Optional<Election> electionOptional = Optional.of(Election.builder().createdAt(new Date()).duration(100).build());
        given(electionRepository.findById(1L)).willReturn(electionOptional);

        final Election election = electionOptional.get();
        final Associate associate = optionalAssociate.get();

        final Vote vote = Vote.builder().value(true).election(election).associate(associate).build();
        given(voteRepository.save(vote)).willReturn(vote);

        final ResponseEntity<Vote> voteResponseEntity =
                votesController.create(VoteRequest.builder().value(VoteValue.YES).associateId(1L).electionId(1L).build());

        assertThat(voteResponseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(voteResponseEntity.getBody(), is(vote));
    }

    @Test(expected = SessionExpired.class)
    @SneakyThrows
    public void givenAnExpiredSessionAnExistingUserShouldShouldTrhownSessionExpired() {
        final Optional<Associate> optionalAssociate = Optional.of(Associate.builder().build());
        given(associateRepository.findById(1L)).willReturn(optionalAssociate);

        final Election election = Election.builder().duration(0).createdAt(new Date()).build();
        given(electionRepository.findById(1L)).willReturn(Optional.of(election));

        votesController.create(VoteRequest.builder().value(VoteValue.YES).associateId(1L).electionId(1L).build());
    }
}