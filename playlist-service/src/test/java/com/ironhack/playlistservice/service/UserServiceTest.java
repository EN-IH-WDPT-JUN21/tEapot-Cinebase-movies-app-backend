package com.ironhack.playlistservice.service;

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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @MockBean
    private PlaylistServiceApplication playlistServiceApplication;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    User user1;
    User user2;

    @BeforeEach
    void setUp() {
        user1 = new User("hellokitty", "htmlerror404");
        user2 = new User("evilnamesake", "let!tbee");
        userRepository.saveAll(List.of(user1, user2));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void getAllUsers() {
        List<UserDTO> list = userService.getAllUsers();
        assertEquals(2, list.size());
    }

    @Test
    void getById() {
        UserDTO testUser = userService.getById(user1.getId());
        assertEquals("hellokitty", testUser.getUsername());
    }

    @Test
    void getByUsername() {
        UserDTO testUser = userService.getByUsername(user1.getUsername());
        assertEquals("hellokitty", testUser.getUsername());
    }

    @Test
    void updateUser() {
        UpdateRequest updateRequest=new UpdateRequest();
        updateRequest.setBio("I love movies");

        userService.updateUser(user1.getId(), updateRequest);

        assertEquals("I love movies", userRepository.findById(user1.getId()).get().getBio());
    }

    @Test
    void deleteUser() {
    }

    @Test
    void createUser() {
    }
}