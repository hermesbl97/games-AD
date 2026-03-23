package com.svalero.games.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {
    private long id;
    private String name;
    private String description;
    private String type;
    private LocalDate releaseDate;
    private float price;
    private String category;
    private long daysToRelease;

}
