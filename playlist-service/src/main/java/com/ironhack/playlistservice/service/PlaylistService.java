package com.ironhack.playlistservice.service;

import com.ironhack.playlistservice.dao.Playlist;
import com.ironhack.playlistservice.dto.PlaylistDTO;
import com.ironhack.playlistservice.repository.PlaylistRepository;
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

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
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

    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
    }

    public void createPlaylist(PlaylistDTO playlistDTO) {
        playlistRepository.save(dtoToDao(playlistDTO));
    }

    public PlaylistDTO getPlaylist(Playlist playlist) {
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setId(playlist.getId());
        playlistDTO.setName(playlist.getName());
        playlistDTO.setUserId(playlist.getUserId());
        playlistDTO.setMovies(playlist.getMovies());
        return playlistDTO;
    }

    public Playlist dtoToDao(PlaylistDTO playlistDTO) {
        Playlist playlist = new Playlist();
        playlist.setName(playlistDTO.getName());
        playlist.setUserId(playlistDTO.getUserId());
        playlist.setMovies(playlistDTO.getMovies());
        return playlist;
    }
}
