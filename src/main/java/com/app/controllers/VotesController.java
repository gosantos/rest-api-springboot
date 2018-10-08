package com.app.controllers;

import com.app.SessionExpired;
import com.app.models.Associate;
import com.app.models.Election;
import com.app.models.Vote;
import com.app.models.VoteRequest;
import com.app.repositories.AssociateRepository;
import com.app.repositories.ElectionRepository;
import com.app.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VotesController {

    private final VoteRepository voteRepository;

    private final ElectionRepository electionRepository;

    private final AssociateRepository associateRepository;

    @Autowired
    public VotesController(VoteRepository voteRepository, ElectionRepository electionRepository, AssociateRepository associateRepository) {
        this.voteRepository = voteRepository;
        this.electionRepository = electionRepository;
        this.associateRepository = associateRepository;
    }

    @PostMapping(value = "/votes/")
    public ResponseEntity<Vote> create(@RequestBody VoteRequest voteRequest) throws SessionExpired, NotFoundException {
        final Associate associate = associateRepository.findById(voteRequest.getAssociateId()).orElseThrow(NotFoundException::new);

        final Election election = electionRepository.findById(voteRequest.getElectionId()).orElseThrow(NotFoundException::new);

        if (election.isSessionExpired()) {
            throw new SessionExpired();
        }

        final Vote vote = Vote.builder().value(voteRequest.getBooleanValue()).associate(associate).election(election).build();

        final Vote save = voteRepository.save(vote);

        return ResponseEntity.ok(save);
    }
}
