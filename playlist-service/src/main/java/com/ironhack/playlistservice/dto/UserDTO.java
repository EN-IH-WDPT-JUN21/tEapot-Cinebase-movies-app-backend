package com.ironhack.playlistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String imageUrl;
    private String bio;
    private List<Long> playlists;

    public UserDTO(Long id, String username, String email, String imageUrl) {
        this.id = id;
        this.username = username;
        this.imageUrl = imageUrl;
    }

    public UserDTO(String imageUrl, String bio, List<Long> playlists) {
        this.imageUrl = imageUrl;
        this.bio = bio;
        this.playlists = playlists;
    }

    public UserDTO(String username, String email) {
        this.username = username;
    }

    public UserDTO(String email) {
        this.email=email;
    }
}
