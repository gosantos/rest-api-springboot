package com.app.controllers;

import com.app.models.Associate;
import com.app.models.AssociateRequest;
import com.app.repositories.AssociateRepository;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
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

        final ResponseEntity<Associate> expectedAssociate = associatesController.getAssociate(associate.getId());
        assertThat(expectedAssociate.getBody(), is(associate));
    }

    @Test
    @SneakyThrows
    public void shouldPostAnAssociate() {
        final Associate associate = Associate.builder().name("Foo").build();

        given(associateRepository.save(associate)).willReturn(associate);

        final AssociateRequest associateRequest = AssociateRequest.builder().name("Foo").build();
        final ResponseEntity<Associate> saveAssociate = associatesController.saveAssociate(associateRequest);

        assertThat(saveAssociate.getStatusCodeValue(), is(200));
        assertThat(saveAssociate.getBody().getName(), is(associateRequest.getName()));
    }
}