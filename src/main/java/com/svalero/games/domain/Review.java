package com.svalero.games.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autogenera el id
    private long id;

    @Column (name = "review_date")
    private LocalDate reviewDate;

    @Column(name = "play_date")
    private LocalDate playDate;

    @Column
    private int rate;

    @Column
    private String description;

    @Column
    private boolean recommendation;

//    @Column
//    private double latitude;
//
//    @Column
//    private double longitude;


    @ManyToOne //es como facilitamos a Springboot la info de que es foreign key
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
