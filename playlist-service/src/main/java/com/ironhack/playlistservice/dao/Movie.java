package com.ironhack.playlistservice.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imdbId;
    private String title;

    @ManyToMany(mappedBy = "movies")
    private List<Playlist> playlists;


    public Movie(String imdbId, String title) {
        this.imdbId = imdbId;
        this.title = title;
        this.playlists = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return imdbId.equals(movie.imdbId) && title.equals(movie.title);
    }
}
