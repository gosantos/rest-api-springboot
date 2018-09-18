package com.app.controllers;

import com.app.models.Associate;
import com.app.repositories.AssociateRepository;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class AssociatesControllerTest {

    private AssociatesController associatesController;

    @Mock
    private AssociateRepository associateRepository;

    @Before
    public void setUp() {
        initMocks(this);

        associatesController = new AssociatesController(associateRepository);
    }

    @Test
    @SneakyThrows
    public void shouldReturnAnAssociateById() {
        final Associate associate = Associate.builder().name("Foo").build();

        given(associateRepository.findById(associate.getId())).willReturn(Optional.of(associate));

        final Associate associateById = associatesController.associates(associate.getId());
        assertThat(associateById, is(associate));
    }
}