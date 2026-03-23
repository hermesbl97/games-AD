package com.svalero.games.service;

import com.svalero.games.domain.Game;
import com.svalero.games.dto.GameDto;
import com.svalero.games.dto.GameOutDto;
import com.svalero.games.exception.GameNotFoundException;
import com.svalero.games.repository.GameRepository;
import com.svalero.games.util.DateUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Game add(Game game){
        return gameRepository.save(game);  //guarda un objeto de tipo game de manera automátca

    }

    public void delete(long id) throws GameNotFoundException {
        Game game = gameRepository.findById(id)
        .orElseThrow(GameNotFoundException::new);

        gameRepository.delete(game);
    }

//    public List<Game> findAll() {
//        List <Game> allGames= gameRepository.findAll();
//        return allGames;
//    }

    public List<GameOutDto> findAll(String category) {
        List <Game> games;

        if (!category.isEmpty()) {
            games = gameRepository.findByCategory(category);
        } else {
            games = gameRepository.findAll();
        }

        return modelMapper.map(games, new TypeToken<List<GameOutDto>>(){}.getType()); //aqui decimos que nos mapee la lista de juegos a una lista de GameOutDtos y que mapee campo a campo los que coincidan

    }

    public List<Game> findByCategory(String category) {
        List <Game> games = gameRepository.findByCategory(category);
        return games;
    }

//    public Game findById(long id) throws GameNotFoundException{
//        Game game = gameRepository.findById(id)
//                .orElseThrow(GameNotFoundException::new);
//
//        return game;
//    }

    public GameDto findById(long id) throws GameNotFoundException{
        Game game = gameRepository.findById(id)
                .orElseThrow(GameNotFoundException::new);

        //para poder calcular los dias que faltan para que salga un juego hariamos:
        GameDto gameDto = modelMapper.map(game, GameDto.class); //mapeame el juego en un gameDto
        gameDto.setDaysToRelease(DateUtil.getDaysBetweenDates(LocalDate.now() ,gameDto.getReleaseDate()));  //calculamos la diferencia de dias desde hoy hasta el dia que sale

        return gameDto;
    }

    public Game modify(long id, Game game) throws GameNotFoundException {
        Game existingGame = gameRepository.findById(id) //nos devolverá el objeto game que le hemos pasado mediante el id
                .orElseThrow(() -> new GameNotFoundException()); //decimos dame el objeto y sino lanzame una excepción
                //.orElseThrow(GameNotFoundException::new);  //es lo mismo de antes escrito de otra manera

        modelMapper.map(game, existingGame);
        existingGame.setId(id);
//        al usar model mapper ya no es necesario esto, lo genera automaticamente
//        existingGame.setName(game.getName());
//        existingGame.setType(game.getType());
//        existingGame.setPrice(game.getPrice());
//        existingGame.setDescription(game.getDescription());
//        existingGame.setReleaseDate(game.getReleaseDate());
//        existingGame.setCategory(game.getCategory());

        return gameRepository.save(existingGame); //como el objeto que le pasamos tiene un id lo modifica. Si no lo tuviera lo daría de alta


    }

}
