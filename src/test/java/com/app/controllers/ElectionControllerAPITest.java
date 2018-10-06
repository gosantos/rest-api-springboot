package com.app.controllers;

import com.app.models.Election;
import com.app.models.Subject;
import com.app.repositories.ElectionRepository;
import com.app.repositories.SubjectRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ElectionController.class)
public class ElectionControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ElectionRepository electionRepository;

    @MockBean
    private SubjectRepository subjectRepository;

    @Test
    public void shouldReturn200WhenGettingAnElection() throws Exception {
        final Subject subject = Subject.builder().title("FOO BAR").build();
        given(subjectRepository.findByTitle("FOO BAR")).willReturn(Optional.of(subject));

        final Election election = Election.builder().id(1L).subject(subject).build();
        given(electionRepository.save(election)).willReturn(election);

        mockMvc.perform(post("/elections/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{ \"subjectTitle\": \"FOO BAR\" }"))
                .andExpect(status().is2xxSuccessful());
    }
}
