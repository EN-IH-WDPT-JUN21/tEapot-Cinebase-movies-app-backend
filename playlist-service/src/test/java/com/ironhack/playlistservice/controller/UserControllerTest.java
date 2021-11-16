package com.ironhack.playlistservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.playlistservice.PlaylistServiceApplication;
import com.ironhack.playlistservice.dao.User;
import com.ironhack.playlistservice.dto.UpdateRequest;
import com.ironhack.playlistservice.dto.UserDTO;
import com.ironhack.playlistservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.ws.rs.core.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    @MockBean
    private PlaylistServiceApplication playlistServiceApplication;

    @Autowired
    private UserController userController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    User user1;
    User user2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        user1 = new User("hellokitty");
        user2 = new User("evilnamesake");
        userRepository.saveAll(List.of(user1, user2));

    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void getAllUsers() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/users")).andExpect(status().isOk()).andReturn();
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("hellokitty"));
    }

    @Test
    void getById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/users/"+user1.getId().toString()))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("hellokitty"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("evilnamesake"));
    }

    @Test
    void getByUsername() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "hellokitty");
        MvcResult mvcResult = mockMvc.perform(
                get("/api/users/user").queryParams(params))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains(user1.getId().toString()));
        assertFalse(mvcResult.getResponse().getContentAsString().contains(user2.getId().toString()));
    }

    @Test
    void updateUser() throws Exception {
        UpdateRequest updateRequest=new UpdateRequest();
        updateRequest.setBio("I love movies");

        String body=objectMapper.writeValueAsString(updateRequest);
        MvcResult mvcResult = mockMvc.perform(patch("/api/users/"+user1.getId().toString())
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("I love movies", userRepository.findById(user1.getId()).get().getBio());
    }

    @Test
    void createUser() throws Exception {
        UserDTO userDTO = new UserDTO("laurapalmer", "pikachuuu");

        String body = objectMapper.writeValueAsString(userDTO);
        MvcResult mvcResult = mockMvc.perform(
                post("/api/users")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        assertEquals(3, userRepository.findAll().size());
    }

    @Test
    void deleteUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/api/users/"+user1.getId().toString()))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(1, userRepository.findAll().size());
    }
}