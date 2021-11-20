package com.ironhack.playlistservice.controller;

import com.ironhack.playlistservice.dto.MovieDTO;
import com.ironhack.playlistservice.dto.PlaylistDTO;
import com.ironhack.playlistservice.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/playlist")
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

    @GetMapping(path = "", params = {"email"})
    @ResponseStatus(HttpStatus.OK)
    public List<PlaylistDTO> getByUserEmail(@RequestParam(value = "email") String email) {
        return playlistService.getByUserEmail(email);
    }

    @PostMapping("/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPlaylist(@PathVariable("email") String email, @RequestBody PlaylistDTO playlistDTO) {
        playlistService.createPlaylist(playlistDTO, email);
    }

    @PatchMapping("/{id}")
    public void updatePlaylist(@PathVariable("id") Long id, @RequestBody MovieDTO movieDTO) {
        playlistService.updatePlaylist(id, movieDTO);
    }

    @DeleteMapping(path = "", params = {"playlistId", "imdbId"})
    public PlaylistDTO deleteMovie(@PathParam("playlistId") Long playlistId, @PathParam("imdbId") String imdbId) {
        return playlistService.deleteMovie(playlistId, imdbId);
    }

    @DeleteMapping("/{id}")
    public void deletePlaylist(@PathVariable("id") Long id) {
        playlistService.deletePlaylist(id);
    }
}
