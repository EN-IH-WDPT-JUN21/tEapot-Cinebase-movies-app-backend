package com.ironhack.playlistservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.playlistservice.PlaylistServiceApplication;
import com.ironhack.playlistservice.dao.User;
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
import org.springframework.web.context.WebApplicationContext;
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
        user1 = new User("hellokitty", "htmlerror404");
        user2 = new User("evilnamesake", "let!tbee");
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
    void getById() {
    }

    @Test
    void getByUsername() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void createUser() {
    }

    @Test
    void deleteUser() {
    }
}