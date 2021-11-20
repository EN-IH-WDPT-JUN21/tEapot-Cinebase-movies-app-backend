package com.ironhack.playlistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserStatsDTO {
    private int playlistsCount;
    private int titlesCount;
    private double avgTitlesInPlaylist;
    private int maxTitlesInPlaylist;
}
