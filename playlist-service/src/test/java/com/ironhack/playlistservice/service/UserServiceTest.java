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

import java.text.ParseException;
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
        user1 = new User("hellokitty");
        user2 = new User("evilnamesake");
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
    void getByEmail() throws ParseException {
        UserDTO testUser = userService.getByEmail(user1.getEmail());
        assertEquals("hellokitty", testUser.getUsername());
    }

    @Test
    void getByUsername() throws ParseException {
        UserDTO testUser = userService.getByEmail(user1.getEmail());
        assertEquals("hellokitty", testUser.getUsername());
    }

    @Test
    void updateUser() {
        UpdateRequest updateRequest=new UpdateRequest();
        updateRequest.setBio("I love movies");

        userService.updateUser(user1.getEmail(), updateRequest);

        assertEquals("I love movies", userRepository.findByEmail(user1.getEmail()).get().getBio());
    }

    @Test
    void deleteUser() {
        userService.deleteUser(user1.getEmail());

        assertEquals(1, userRepository.findAll().size());
    }

    @Test
    void createUser() throws ParseException {
        UserDTO userDTO = new UserDTO("laurapalmer");

        userService.createUser(userDTO);
        assertEquals(3, userRepository.findAll().size());
    }
}