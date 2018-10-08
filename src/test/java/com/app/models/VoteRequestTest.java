package com.app.models;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class VoteRequestTest {
    @Test
    public void shouldReturnTrueWhenYes() {
        final VoteRequest voteRequest = VoteRequest.builder().value(VoteValue.YES).build();

        assertThat(voteRequest.getBooleanValue(), is(true));
    }

    @Test
    public void shouldReturnTrueWhenNo() {
        final VoteRequest voteRequest = VoteRequest.builder().value(VoteValue.NO).build();

        assertThat(voteRequest.getBooleanValue(), is(false));
    }
}