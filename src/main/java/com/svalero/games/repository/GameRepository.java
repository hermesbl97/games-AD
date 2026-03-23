package com.svalero.games.repository;

import com.svalero.games.domain.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public  interface  GameRepository  extends CrudRepository<Game, Long> {

    List<Game> findAll();
    List<Game> findByCategory(String category); //como no nos viene el metodo como opcion, lo creamos
}
