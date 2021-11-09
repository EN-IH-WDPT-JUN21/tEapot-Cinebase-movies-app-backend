package com.ironhack.userservice.proxy;

import com.ironhack.userservice.dto.PlaylistDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@FeignClient("PLAYLIST-SERVICE")
public interface PlaylistServiceProxy {

    @GetMapping("/api/playlist")
    List<PlaylistDTO> getAllPlaylists();

    @GetMapping("/api/playlist/{id}")
    PlaylistDTO getById(@PathVariable("id") Long id);
}
