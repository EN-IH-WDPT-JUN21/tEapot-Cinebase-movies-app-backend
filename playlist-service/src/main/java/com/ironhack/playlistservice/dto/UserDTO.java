package com.ironhack.playlistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
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
    private String imageUrl;
    private String bio;
    private List<Long> playlists;

    public UserDTO(String email) {
        this.email = email;
        this.bio="";
        this.username="";
        this.imageUrl="";
        this.playlists=new ArrayList<>();
    }
}
