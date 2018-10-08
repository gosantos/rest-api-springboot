package com.app.models;

import org.junit.Test;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ElectionTest {

    @Test
    public void shouldReturnTrueWhenSessionIsExpired() {
        final Date createdAt = new Date();

        final Election election = Election.builder().createdAt(createdAt).duration(-1).build();

        assertThat(election.isSessionExpired(), is(true));
    }

    @Test
    public void shouldReturnFalseWhenSessionIsntExpired() {
        final Date createdAt = new Date();

        final Election election = Election.builder().createdAt(createdAt).duration(60).build();

        assertThat(election.isSessionExpired(), is(false));
    }
}