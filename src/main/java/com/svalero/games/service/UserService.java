package com.svalero.games.service;

import com.svalero.games.domain.Game;
import com.svalero.games.domain.User;
import com.svalero.games.exception.GameNotFoundException;
import com.svalero.games.exception.UserNotFoundException;
import com.svalero.games.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User add(User user){
        return userRepository.save(user);  //guarda un objeto de tipo game de manera automátca

    }

    public void delete(long id) throws UserNotFoundException {

    }

    public List<User> findAll() {
        userRepository.findAll();
        return userRepository.findAll();
    }

    public List<User> findByCategory(String category) {
        return null;
    }


    public User findById(long id) throws UserNotFoundException{

        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public User modify(long id, User user) throws UserNotFoundException {
        return null;
    }

}
