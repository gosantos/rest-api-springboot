package com.app.controllers;

import com.app.models.Subject;
import com.app.repositories.SubjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SubjectsController.class)
public class SubjectsControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectRepository subjectRepository;

    @Test
    public void shouldReturn200WhenPostingASubject() throws Exception {
        final Subject subject = Subject.builder().id(123L).title("Foo").build();
        given(subjectRepository.save(subject)).willReturn(subject);

        mockMvc.perform(post("/subjects/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{ \"title\": \"Foo\" }"))
                .andExpect(status().is2xxSuccessful());
    }
}