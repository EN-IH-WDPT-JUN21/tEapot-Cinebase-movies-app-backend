package com.ironhack.playlistservice.dao;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @OneToOne
    @JoinColumn(name = "imageId")
    Image image;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(unique = true)
    private String email;
    private String username;
    @Lob
    @Column
    private String bio;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Long> playlists;

    public User(String email) {
        this.email = email;
        this.bio = "";
        this.username = "";
        this.playlists = new ArrayList<>();
    }
}
