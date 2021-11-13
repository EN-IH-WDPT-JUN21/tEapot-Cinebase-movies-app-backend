package com.ironhack.playlistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateRequest {
    private String imageUrl;
    private String bio;
    private Long playlistId;

    public UpdateRequest(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UpdateRequest(Long playlistId) {
        this.playlistId = playlistId;
    }
}
