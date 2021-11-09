package com.ironhack.playlistservice.controller;

import com.ironhack.playlistservice.dao.Playlist;
import com.ironhack.playlistservice.dto.PlaylistDTO;
import com.ironhack.playlistservice.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {
    @Autowired
    PlaylistService playlistService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PlaylistDTO> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlaylistDTO getById(@PathVariable("id") Long id) {
        return playlistService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPlaylist(@RequestBody PlaylistDTO playlistDTO) {
        playlistService.createPlaylist(playlistDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePlaylist(@PathVariable("id") Long id) {
        playlistService.deletePlaylist(id);
    }
}
