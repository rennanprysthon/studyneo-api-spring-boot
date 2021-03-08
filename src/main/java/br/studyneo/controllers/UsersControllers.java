package br.studyneo.controllers;

import br.studyneo.model.Users;
import br.studyneo.repositories.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "users")
public class UsersControllers {
    private UsersRepository repository;

    public UsersControllers(UsersRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Users>> findAll() {
        List<Users> users = repository.findAll();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(users);
    }
}
