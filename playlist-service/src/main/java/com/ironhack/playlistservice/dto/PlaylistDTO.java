package com.ironhack.playlistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class PlaylistDTO {

    private Long id;
    private Long userId;
    private String name;
    private Set<MovieDTO> movies;

    public PlaylistDTO(Long id, Long userId, String name, Set<MovieDTO> movies) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.movies = movies;
    }
    public PlaylistDTO(Long id, Long userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }

    public PlaylistDTO(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
