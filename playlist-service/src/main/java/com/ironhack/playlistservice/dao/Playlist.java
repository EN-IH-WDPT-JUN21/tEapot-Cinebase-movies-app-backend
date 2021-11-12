package com.ironhack.playlistservice.dao;

import com.ironhack.playlistservice.dto.MovieDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
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

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MovieDTO> movies;

    public Playlist(Long userId, String name, List<MovieDTO> movies) {
        this.userId = userId;
        this.name = name;
        this.movies = movies;
    }
}
