package com.ironhack.playlistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlaylistDTO {

    private Long id;
    private Long userId;
    private String name;
    private List<MovieDTO> movies;

    public PlaylistDTO(Long id, Long userId, String name, List<MovieDTO> movies) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.movies = movies;
    }
}
