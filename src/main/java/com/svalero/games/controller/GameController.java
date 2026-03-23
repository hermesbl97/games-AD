package com.svalero.games.controller;

import com.svalero.games.domain.Game;
import com.svalero.games.dto.GameDto;
import com.svalero.games.dto.GameOutDto;
import com.svalero.games.exception.ErrorResponse;
import com.svalero.games.exception.GameNotFoundException;
import com.svalero.games.service.GameService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;
    @Autowired ModelMapper modelMapper;

    @GetMapping("/games")
//    public ResponseEntity<List <Game>> getAll(@RequestParam(value = "category", defaultValue = "") String category) {
//        // si no me pasan ningún valor en el campo estará vacío, mostrará tod. Sino nos dará un filtrado de ese campo
//        List <Game> games;
//        if (!category.isEmpty()) {
//            games = gameService.findByCategory(category);
//        } else {
//            games = gameService.findAll();
//        }
//
//        return ResponseEntity.ok(games);
//    }  //Como queremos solo mostrar algun dato especifico y no todos. Usamos el GameOutDto en vez del Game
    public ResponseEntity<List <GameOutDto>> getAll(@RequestParam(value = "category", defaultValue = "") String category) {
        // si no me pasan ningún valor en el campo estará vacío, mostrará tod. Sino nos dará un filtrado de ese campo
//        List <Game> games;
        List <GameOutDto> gamesOutDto = gameService.findAll(category);

        return ResponseEntity.ok(gamesOutDto);
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<GameDto> get(@PathVariable long id) throws GameNotFoundException {
        GameDto gameDto = gameService.findById(id);
        return ResponseEntity.ok(gameDto);
    }

    @PostMapping("/games")
    public ResponseEntity<Game> addGame(@Valid @RequestBody Game game) {  //Nos va a pasar los datos para rellenar la tabla games. Y ponemos el requestbody ya que nos lo pasará en el cuerpo de la llamada
        Game newGame = gameService.add(game);
        return new ResponseEntity<>(newGame,HttpStatus.CREATED);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newGame); //es lo mismo que la linea anterior
    }

    @PutMapping("/games/{id}")
    public ResponseEntity<Game> modifyGame(@PathVariable long id, @RequestBody Game game) throws GameNotFoundException { //no nos pasará el id
        Game newGame = gameService.modify(id, game);
        return ResponseEntity.ok(newGame);
        // return new ResponseEntity<>(newGame, HttpStatus.OK); //es lo mismo esto que la linea anterior
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable long id) throws GameNotFoundException{
        gameService.delete(id);
        return ResponseEntity.noContent().build(); //nos devuelve no content al borrar correctamente
    }

    @ExceptionHandler(GameNotFoundException.class) //cuando la excepción ocurra saltará a este manejador
    public ResponseEntity<ErrorResponse> handleException(GameNotFoundException gnfe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(404, "not-found", "The game does not exist");

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        //vamos a devolver una nueva respuesta que contendrá mi errorResponse seguido del HttpStatus not found
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException manve) {
        Map<String,String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> { //dame los resultados de la excepción, dame los errores y hago un for each
            String fieldname = ((FieldError) error).getField(); //cogemos de cada error el nombre del error
            String message = error.getDefaultMessage(); //y su mensaje
            errors.put(fieldname,message);

        });
        ErrorResponse errorResponse = ErrorResponse.validationError(400, "bad-request", "<Validation error>", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
