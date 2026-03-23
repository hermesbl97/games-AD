package com.svalero.games.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewInDto { //Aquí en esta clase defino el objeto java con sólo lo que queremos

    private LocalDate playDate;
    @Min(value = 1, message = "Rate must be between 1-5")
    @Max(value = 5, message = "Rate must be between 1-5")
    private int rate;
    @NotEmpty(message = "Description is mandatory")
    private String description;
    private boolean recommendation;
    private long userId;
    //private long gameId; si lo recogemos desde el endpoint. Ya no nos hace falta

}
