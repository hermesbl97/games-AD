package com.svalero.games.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity (name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String username;

    @Column
    private String name;

    @Column
    private String surname;

    @Column (name = "barth_date")
    private LocalDate birthDate;

    @Column (name = "registration_date")
    private LocalDate registerDate;


    @JsonBackReference //para que no nos muestre las reviews cuando pidamos toda la info del usuario
    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

}
