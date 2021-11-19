package com.ironhack.playlistservice.service;

import com.ironhack.playlistservice.PlaylistServiceApplication;
import com.ironhack.playlistservice.controller.PlaylistController;
import com.ironhack.playlistservice.dao.Movie;
import com.ironhack.playlistservice.dao.Playlist;
import com.ironhack.playlistservice.dao.User;
import com.ironhack.playlistservice.dto.MovieDTO;
import com.ironhack.playlistservice.dto.PlaylistDTO;
import com.ironhack.playlistservice.dto.UserDTO;
import com.ironhack.playlistservice.repository.MovieRepository;
import com.ironhack.playlistservice.repository.PlaylistRepository;
import com.ironhack.playlistservice.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlaylistServiceTest {

    @MockBean
    private PlaylistServiceApplication playlistServiceApplication;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private MovieRepository movieRepository;

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
    @Order(1)
    void getAllPlaylists() {
        List<PlaylistDTO> list = playlistService.getAllPlaylists();
        assertEquals(2, list.size());
    }

    @Test
    @Order(1)
    void getById() {
        PlaylistDTO playlistDTO = playlistService.getById(playlist1.getId());
        assertEquals("My movies", playlistDTO.getName());
    }

    @Test
    @Order(1)
    void getByUserId() {
        List<PlaylistDTO> list = playlistService.getByUserEmail(user1.getEmail());
        assertEquals(1, list.size());
    }


    @Test
    @Order(1)
    void createPlaylist() {
        PlaylistDTO playlistDTO = new PlaylistDTO(user1.getId(), "awesome stuff");

        playlistService.createPlaylist(playlistDTO);
        assertEquals(3, playlistRepository.findAll().size());
    }

    @Test
    @Order(1)
    void updatePlaylist() {
        MovieDTO movieDTO=new MovieDTO();
        movieDTO.setImdbId("tt0110912");
        movieDTO.setTitle("Pulp Fiction");

        playlistService.updatePlaylist(playlist1.getId(), movieDTO);

        assertEquals(3, playlistRepository.findById(playlist1.getId()).get().getMovies().size());
    }

    @Test
    @Order(2)
    void deleteMovie() {

        playlistService.deleteMovie(playlist1.getId(), movie1.getImdbId());
        assertEquals(1, playlistRepository.findById(playlist1.getId()).get().getMovies().size());
    }

    @Test
    @Order(3)
    void deletePlaylist() {
        playlistService.deletePlaylist(playlist1.getId());

        assertEquals(1, playlistRepository.findAll().size());
    }
}