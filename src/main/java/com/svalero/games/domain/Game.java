    package com.svalero.games.domain;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.Min;
    import jakarta.validation.constraints.NotNull;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.time.LocalDate;
    import java.util.List;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity(name = "games")
    //@Table(name = "games")  no seria imprescindible //adjudicamos nombre que queremos que tenga la tabla en la base de datos
    public class Game {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)   //con esto le decimos que es un valor autonumerico para que se generen los valores de id por sí mismos
        private long id;
        @Column
        @NotNull (message = "Name is mandatory")
        private String name;
        @Column
        private String description;
        @Column
        private String type;
        @Column (name = "release_date") //Le facilitamos como queremos que lo nombre en la tabla ya que en una BD necesitamos la nomenclatura LowerSnakeCase y no LowerCamelCase
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate releaseDate;
        @Column
        @Min(value = 0, message = "The price must be a positive number")
        private float price;
        @Column
        private String category;
        @Column
        private String comments;

        @OneToMany(mappedBy = "game")
        private List<Review> review;
    }
