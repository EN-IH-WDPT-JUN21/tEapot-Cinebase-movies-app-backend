package com.ironhack.playlistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String bio;
    private List<PlaylistDTO> playlists;
    private String imageId;

    public UserDTO(String email) {
        this.email = email;
        this.bio = "";
        this.username = "";
        this.playlists = new ArrayList<>();
    }
}
