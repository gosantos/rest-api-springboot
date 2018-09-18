package com.app.repositories;

import com.app.models.Associate;
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
public class AssociateRepositoryIntegrationTest {

    @Autowired
    private AssociateRepository associateRepository;

    @After
    public void tearDown() {
        associateRepository.deleteAll();
    }

    @Test
    public void shouldSaveAndFetchAnAssociate() {
        final Associate foo = Associate.builder().name("Foo").build();
        associateRepository.save(foo);

        final Optional<Associate> associateFoundById = associateRepository.findById(foo.getId());

        assertThat(associateFoundById, is(Optional.of(foo)));
    }
}