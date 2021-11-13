package com.ironhack.playlistservice.controller;

import com.ironhack.playlistservice.dao.Playlist;
import com.ironhack.playlistservice.dto.MovieDTO;
import com.ironhack.playlistservice.dto.PlaylistDTO;
import com.ironhack.playlistservice.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/playlist")
@CrossOrigin(origins="http://localhost:4200")
public class PlaylistController {
    @Autowired
    PlaylistService playlistService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PlaylistDTO> getAllPlaylists(Principal principle) {
        return playlistService.getAllPlaylists();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlaylistDTO getById(@PathVariable("id") Long id) {
        return playlistService.getById(id);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<PlaylistDTO> getByUserId(@RequestParam (value="userid") Long userId) {
        return playlistService.getByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPlaylist(@RequestBody PlaylistDTO playlistDTO) {
        playlistService.createPlaylist(playlistDTO);
    }

    @PatchMapping("/{id}")
    public void updatePlaylist(@PathVariable("id") Long id, @RequestBody MovieDTO movieDTO){
         playlistService.updatePlaylist(id, movieDTO);
    }

    @PatchMapping("/delete/{id}")
    public void deleteMovie(@PathVariable("id") Long id, @RequestBody MovieDTO movieDTO){
        playlistService.deleteMovie(id, movieDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePlaylist(@PathVariable("id") Long id) {
        playlistService.deletePlaylist(id);
    }
}
