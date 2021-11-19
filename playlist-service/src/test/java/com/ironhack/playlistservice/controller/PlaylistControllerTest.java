package com.ironhack.playlistservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.playlistservice.PlaylistServiceApplication;
import com.ironhack.playlistservice.dao.Movie;
import com.ironhack.playlistservice.dao.Playlist;
import com.ironhack.playlistservice.dao.User;
import com.ironhack.playlistservice.dto.MovieDTO;
import com.ironhack.playlistservice.dto.PlaylistDTO;
import com.ironhack.playlistservice.dto.UpdateRequest;
import com.ironhack.playlistservice.dto.UserDTO;
import com.ironhack.playlistservice.repository.MovieRepository;
import com.ironhack.playlistservice.repository.PlaylistRepository;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PlaylistControllerTest {

    @MockBean
    private PlaylistServiceApplication playlistServiceApplication;

    @Autowired
    private PlaylistController playlistController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private MovieRepository movieRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    User user1;
    User user2;

    Playlist playlist1;
    Playlist playlist2;
    Movie movie1;
    Movie movie2;
    Movie movie3;
    List<Movie> movies1 = new ArrayList<>();
    List<Movie> movies2 = new ArrayList<>();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        user1 = new User("hellokitty");
        user2 = new User("evilnamesake");
        userRepository.saveAll(List.of(user1, user2));
        movie1 = new Movie("tt1375666", "Inception");
        movie2 = new Movie("tt2382320", "No Time to Die");
        movie3 = new Movie("tt9764386", "30 Monedas");
        movieRepository.saveAll(List.of(movie1, movie2, movie3));
        movies1.add(movie1);
        movies1.add(movie2);
        movies2.add(movie3);
        playlist1= new Playlist(user1, "My movies", movies1);
        playlist2= new Playlist(user2, "My series", movies2);
        playlistRepository.saveAll(List.of(playlist1, playlist2));
    }

    @AfterEach
    void tearDown() {
        playlistRepository.deleteAll();
        movieRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getAllPlaylists() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/playlist")).andExpect(status().isOk()).andReturn();
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("My movies"));
    }

    @Test
    void getById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/playlist/"+playlist1.getId().toString()))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("My movies"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("My series"));
    }

    @Test
    void getByUserId() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("userid", String.valueOf(user1.getId()));
        MvcResult mvcResult = mockMvc.perform(
                get("/api/playlist/user").queryParams(params))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains(playlist1.getId().toString()));
        assertFalse(mvcResult.getResponse().getContentAsString().contains(playlist2.getId().toString()));
    }

    @Test
    void createPlaylist() throws Exception {
        PlaylistDTO playlistDTO = new PlaylistDTO(user1.getId(), "awesome stuff");

        String body = objectMapper.writeValueAsString(playlistDTO);
        MvcResult mvcResult = mockMvc.perform(
                post("/api/playlist")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        assertEquals(3, playlistRepository.findAll().size());
    }

    @Test
    void updatePlaylist() throws Exception {
        MovieDTO movieDTO=new MovieDTO();
        movieDTO.setImdbId("tt0110912");
        movieDTO.setTitle("Pulp Fiction");

        String body=objectMapper.writeValueAsString(movieDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/api/playlist/"+playlist1.getId().toString())
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(3, playlistRepository.findById(playlist1.getId()).get().getMovies().size());
    }

    @Test
    void deleteMovie() throws Exception {

        MvcResult mvcResult = mockMvc.perform(delete("/api/playlist/delete")
                .param("playlistId", playlist1.getId().toString())
                .param("imdbId", movie1.getImdbId()))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(1, playlistRepository.findById(playlist1.getId()).get().getMovies().size());
    }

    @Test
    void deletePlaylist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/api/playlist/"+playlist1.getId().toString()))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(1, playlistRepository.findAll().size());
    }
}