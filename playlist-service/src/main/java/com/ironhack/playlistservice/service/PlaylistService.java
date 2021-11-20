package com.ironhack.playlistservice.service;

import com.ironhack.playlistservice.dao.Movie;
import com.ironhack.playlistservice.dao.Playlist;
import com.ironhack.playlistservice.dto.MovieDTO;
import com.ironhack.playlistservice.dto.PlaylistDTO;
import com.ironhack.playlistservice.repository.MovieRepository;
import com.ironhack.playlistservice.repository.PlaylistRepository;
import com.ironhack.playlistservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public PlaylistService(PlaylistRepository playlistRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public List<PlaylistDTO> getAllPlaylists() {
        List<PlaylistDTO> playlistDTOS = new ArrayList<>();
        var playlists = playlistRepository.findAll();
        for (Playlist playlist : playlists) {
            PlaylistDTO playlistDTO = getPlaylist(playlist);
            playlistDTOS.add(playlistDTO);
        }
        return playlistDTOS;
    }

    public PlaylistDTO getById(Long id) {
        Optional<Playlist> playlist = playlistRepository.findById(id);
        if (playlist.isPresent()) {
            return getPlaylist(playlist.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no Playlist with id " + id);
        }
    }

    public List<PlaylistDTO> getByUserId(Long userId) {
        List<PlaylistDTO> playlistDTOS = new ArrayList<>();
        var playlists = playlistRepository.findByUserId(userId);
        for (Playlist playlist : playlists) {
            PlaylistDTO playlistDTO = getPlaylist(playlist);
            playlistDTOS.add(playlistDTO);
        }
        return playlistDTOS;
    }

    public void deletePlaylist(Long id) {
        var playlist = playlistRepository.findById(id);
        var user = userRepository.findById(playlist.get().getUserId());
        if(playlist.isPresent()) {
            user.get().getPlaylists().remove(playlist.get().getId());
            userRepository.save(user.get());
            playlistRepository.deleteById(id);
        }
    }

    public void createPlaylist(PlaylistDTO playlistDTO) {
        var user = userRepository.findById(playlistDTO.getUserId());
        playlistRepository.save(dtoToDao(playlistDTO));
        user.get().getPlaylists().add(playlistDTO.getId());
        userRepository.save(user.get());
    }

    public void updatePlaylist(Long id, MovieDTO movieDTO){
        var playlist = playlistRepository.findById(id);
        var movie = movieRepository.findByImdbId(movieDTO.getImdbId());
        if(!movie.isPresent()){
            movieRepository.save(movieToDao(movieDTO));
        }
        if(playlist.isPresent()){
            playlist.get().addMovie(movieRepository.findByImdbId(movieDTO.getImdbId()).get());
            playlistRepository.save(playlist.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no Playlist with id " + id);
        }
    }

    public void deleteMovie(Long id, String imdbId){
        var playlist = playlistRepository.findById(id);
        var movie = movieRepository.findByImdbId(imdbId);
        if(playlist.isPresent() && movie.isPresent()){
            playlist.get().deleteMovie(movie.get());
            playlistRepository.save(playlist.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no Playlist with id " + id);
        }
    }

    //**** UTIL METHODS *****
    public PlaylistDTO getPlaylist(Playlist playlist) {
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setId(playlist.getId());
        playlistDTO.setName(playlist.getName());
        playlistDTO.setUserId(playlist.getUserId());

        if(playlist.getMovies()!=null) {
            List<MovieDTO> moviesDTO = new ArrayList<>();
            var movies = playlist.getMovies();
            for (Movie movie : movies) {
                moviesDTO.add(movieToDto(movie));
            }
            playlistDTO.setMovies(moviesDTO);
        }
        return playlistDTO;
    }

    public Playlist dtoToDao(PlaylistDTO playlistDTO) {
        Playlist playlist = new Playlist();
        playlist.setName(playlistDTO.getName());
        playlist.setUserId(playlistDTO.getUserId());

        if(playlistDTO.getMovies()!=null) {
            List<Movie> movies = new ArrayList<>();
            var moviesDTO = playlistDTO.getMovies();
            for (MovieDTO movieDTO : moviesDTO) {
                movies.add(movieToDao(movieDTO));
            }
            playlist.setMovies(movies);
        }
        return playlist;
    }

    public Movie movieToDao(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setImdbId(movieDTO.getImdbId());
        return movie;
    }

    public MovieDTO movieToDto(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setImdbId(movieDTO.getImdbId());
        return movieDTO;
    }



}
