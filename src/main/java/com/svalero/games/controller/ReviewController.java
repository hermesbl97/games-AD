package com.svalero.games.controller;

import com.svalero.games.domain.Game;
import com.svalero.games.domain.Review;
import com.svalero.games.domain.User;
import com.svalero.games.dto.GameDto;
import com.svalero.games.dto.ReviewInDto;
import com.svalero.games.exception.ErrorResponse;
import com.svalero.games.exception.GameNotFoundException;
import com.svalero.games.exception.ReviewNotFoundException;
import com.svalero.games.exception.UserNotFoundException;
import com.svalero.games.service.GameService;
import com.svalero.games.service.ReviewService;
import com.svalero.games.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;

    @GetMapping("/reviews")
    public ResponseEntity<List <Review>> getAll() {

        List<Review> reviews = reviewService.findAll();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/games/{gameId}/reviews")
    public ResponseEntity<List <Review>> getGamesReviews(@PathVariable long gameId) throws GameNotFoundException {
        GameDto gameDto  = gameService.findById(gameId);
        List<Review> reviews = reviewService.findByGame(gameDto);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<Review> get(@PathVariable long id) throws ReviewNotFoundException {
        return null;
    }

    @PostMapping("/games/{gameId}/reviews")
    public ResponseEntity<Review> addReview(@Valid @RequestBody ReviewInDto reviewInDto, @PathVariable long gameId)
            throws GameNotFoundException, UserNotFoundException {

        //GameDto gameDto = gameService.findById(reviewInDto.getGameId()); //al cambiar end point no lo recogemos exactamente asi
        GameDto gameDto = gameService.findById(gameId);
        User user = userService.findById(reviewInDto.getUserId());
        Review review = reviewService.add(reviewInDto, gameDto, user);
        reviewService.add(reviewInDto, gameDto, user);
        return ResponseEntity.ok(review);
    }

    @PutMapping("/reviews/{id}")
    public ResponseEntity<Review> modifyReview(@PathVariable long id, @RequestBody Review review) throws ReviewNotFoundException { //no nos pasará el id
       return null;
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable long id) throws ReviewNotFoundException {
        return null;
    }

    @ExceptionHandler(ReviewNotFoundException.class) //cuando la excepción ocurra saltará a este manejador
    public ResponseEntity<ErrorResponse> handleException(ReviewNotFoundException rnfe) {
        ErrorResponse errorResponse =  ErrorResponse.generalError(404, "not-found", "The game does not exist");

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        //vamos a devolver una nueva respuesta que contendrá mi errorResponse seguido del HttpStatus not found
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException unfe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(404, "not-found", "The user does not exist");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        //vamos a devolver una nueva respuesta que contendrá mi errorResponse seguido del HttpStatus not found
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
