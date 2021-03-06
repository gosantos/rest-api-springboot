package com.app.controllers;

import com.app.models.Associate;
import com.app.repositories.AssociateRepository;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AssociatesController.class)
public class AssociatesControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssociateRepository associateRepository;

    @Test
    public void shouldReturn200WhenGettingAnAssociate() throws Exception {
        final Associate associate = Associate.builder().id(123L).name("Foo").votes(Sets.newHashSet()).build();
        given(associateRepository.findById(associate.getId())).willReturn(Optional.of(associate));

        mockMvc.perform(get("/associates/123"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("{\"id\":123,\"name\":\"Foo\",\"votes\":[]}"));
    }

    @Test
    public void shouldReturn404WhenFetchingNonExistingAssociate() throws Exception {
        mockMvc.perform(get("/associates/321"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturn200WhenPostingAnAssociate() throws Exception {
        final Associate associate = Associate.builder().id(123L).name("Foo").votes(Sets.newHashSet()).build();
        given(associateRepository.save(associate)).willReturn(associate);

        mockMvc.perform(post("/associates/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{ \"name\": \"Foo\" }"))
                .andExpect(status().is2xxSuccessful());
    }
}