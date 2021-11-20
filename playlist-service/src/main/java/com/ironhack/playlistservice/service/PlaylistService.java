package com.ironhack.playlistservice.service;

import com.ironhack.playlistservice.dao.Movie;
import com.ironhack.playlistservice.dao.Playlist;
import com.ironhack.playlistservice.dto.MovieDTO;
import com.ironhack.playlistservice.dto.PlaylistDTO;
import com.ironhack.playlistservice.repository.MovieRepository;
import com.ironhack.playlistservice.repository.PlaylistRepository;
import com.ironhack.playlistservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<PlaylistDTO> getByUserEmail(String email) {
        List<PlaylistDTO> playlistDTOS = new ArrayList<>();
        var playlists = playlistRepository.findByUserId_Email(email);
        for (Playlist playlist : playlists) {
            PlaylistDTO playlistDTO = getPlaylist(playlist);
            playlistDTOS.add(playlistDTO);
        }
        return playlistDTOS;
    }

    @Transactional
    public void deletePlaylist(Long id) {
        var playlist = playlistRepository.findById(id);
        if (playlist.isPresent()) {
            var movies = playlist.get().getMovies();
            var user = userRepository.findById(playlist.get().getUser().getId());
            user.get().getPlaylists().remove(playlist.get().getId());
            userRepository.save(user.get());
            playlistRepository.deleteById(id);

            for (Movie movie : movies) {
                if (movie.getPlaylists().size() == 0) {
                    movieRepository.deleteById(movie.getId());
                }
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no playlist with id " + id);
        }


    }

    @Transactional
    public void createPlaylist(PlaylistDTO playlistDTO, String email) {
        var user = userRepository.findByEmail(email);
        if (playlistDTO.getUserId() == null || playlistDTO.getUserId() < 1) {
            playlistDTO.setUserId(user.get().getId());
        }
        playlistRepository.save(dtoToDao(playlistDTO));
        user.get().getPlaylists().add(playlistDTO.getId());
        userRepository.save(user.get());
    }

    @Transactional
    public void updatePlaylist(Long id, MovieDTO movieDTO) {
        var playlist = playlistRepository.findById(id);
        var movie = movieRepository.findByImdbId(movieDTO.getImdbId());
        if (!movie.isPresent()) {
            movieRepository.save(movieToDao(movieDTO));
        }
        if (playlist.isPresent()) {
            playlist.get().addMovie(movieRepository.findByImdbId(movieDTO.getImdbId()).get());
            playlistRepository.save(playlist.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no Playlist with id " + id);
        }
    }

    @Transactional
    public PlaylistDTO deleteMovie(Long id, String imdbId) {
        var playlist = playlistRepository.findById(id);
        var movie = movieRepository.findByImdbId(imdbId);
        if (playlist.isPresent() && movie.isPresent()) {
            playlist.get().deleteMovie(movie.get());
            playlistRepository.save(playlist.get());
            Movie movieRemoved = movieRepository.getById(movie.get().getId());
            if (movieRemoved.getPlaylists().size() == 0) {
                movieRepository.deleteById(movieRemoved.getId());
            }
            return getPlaylist(playlistRepository.save(playlist.get()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no Playlist with id " + id);
        }
    }

    //**** UTIL METHODS *****
    public PlaylistDTO getPlaylist(Playlist playlist) {
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setId(playlist.getId());
        playlistDTO.setName(playlist.getName());
        playlistDTO.setUserId(playlist.getUser().getId());

        if (playlist.getMovies() != null) {
            Set<MovieDTO> moviesDTO = new HashSet<>();
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
        playlist.setUser(userRepository.getById(playlistDTO.getUserId()));

        if (playlistDTO.getMovies() != null) {
            Set<Movie> movies = new HashSet<>();
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
        movieDTO.setImdbId(movie.getImdbId());
        return movieDTO;
    }
}
