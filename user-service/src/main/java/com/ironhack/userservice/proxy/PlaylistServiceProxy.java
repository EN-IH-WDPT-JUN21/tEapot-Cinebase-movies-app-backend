package com.ironhack.userservice.proxy;

import com.ironhack.userservice.dto.MovieDTO;
import com.ironhack.userservice.dto.PlaylistDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("PLAYLIST-SERVICE")
public interface PlaylistServiceProxy {

    @GetMapping("/api/playlist")
    List<PlaylistDTO> getAllPlaylists();

    @GetMapping("/api/playlist/{id}")
    PlaylistDTO getById(@PathVariable("id") Long id);

    @GetMapping("/api/playlist/user")
    List<PlaylistDTO> getByUserId(@RequestParam Long userId);

    @PatchMapping("/api/playlist/{id}")
    void updatePlaylist(@PathVariable("id") Long id, @RequestBody MovieDTO movieDTO);
}
