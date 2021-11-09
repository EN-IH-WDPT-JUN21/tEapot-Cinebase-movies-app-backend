package com.ironhack.playlistservice.dao;

import com.ironhack.playlistservice.dto.MovieDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String name;
    private List<MovieDTO> movies;

    public Playlist(Long userId, String name, List<MovieDTO> movies) {
        this.userId = userId;
        this.name = name;
        this.movies = movies;
    }
}
