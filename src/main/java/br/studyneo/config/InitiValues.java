package br.studyneo.config;

import br.studyneo.model.Users;
import br.studyneo.repositories.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class InitiValues implements CommandLineRunner {

    private UsersRepository repository;

    public InitiValues(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Users> users = repository.findAll();
        System.out.println(users);
    }
}
