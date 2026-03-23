package com.svalero.games.controller;

import com.svalero.games.domain.Game;
import com.svalero.games.domain.User;
import com.svalero.games.exception.ErrorResponse;
import com.svalero.games.exception.GameNotFoundException;
import com.svalero.games.exception.UserNotFoundException;
import com.svalero.games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List <User>> getAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> get(@PathVariable long id) throws UserNotFoundException {
        return null;
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {  //Nos va a pasar los datos para rellenar la tabla games. Y ponemos el requestbody ya que nos lo pasará en el cuerpo de la llamada
        User newUser = userService.add(user);
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> modifyUser(@PathVariable long id, @RequestBody User user) throws UserNotFoundException { //no nos pasará el id
       return null;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) throws UserNotFoundException{
        return null;
    }

    @ExceptionHandler(UserNotFoundException.class) //cuando la excepción ocurra saltará a este manejador
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException unfe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(404, "not-found", "The game does not exist");

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        //vamos a devolver una nueva respuesta que contendrá mi errorResponse seguido del HttpStatus not found
    }


}
