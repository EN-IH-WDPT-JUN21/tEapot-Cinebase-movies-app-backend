package com.ironhack.playlistservice.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
    private List<Movie> movies;

    public Playlist(Long userId, String name, List<Movie> movies) {
        this.userId = userId;
        this.name = name;
        this.movies = movies;
    }

    public void addMovie(Movie movie){
        if(!this.movies.contains(movie) && this.movies.size()<10){
            this.movies.add(movie);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requirements not met");
        }
    }

    public void deleteMovie(Movie movie){
        if(this.movies.contains(movie)){
            this.movies.remove(movie);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no such movie in the playlist");
        }
    }
}
