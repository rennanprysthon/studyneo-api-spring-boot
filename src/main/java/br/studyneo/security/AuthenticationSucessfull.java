package br.studyneo.security;

import br.studyneo.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationSucessfull {
    private String token;
    private Users users;
}
