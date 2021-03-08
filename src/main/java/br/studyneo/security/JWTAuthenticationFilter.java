package br.studyneo.security;

import br.studyneo.config.ResponseError;
import br.studyneo.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req,
            HttpServletResponse res) throws AuthenticationException {
        try {
            Credenciais creds = new ObjectMapper()
                    .readValue(req.getInputStream(), Credenciais.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>());

            Authentication auth = authenticationManager.authenticate(authToken);
            return  auth;

        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse response,
            FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        Users user = ((Users) auth.getPrincipal());
        String username = user.getUsername();
        String token = jwtUtil.generateToken(username);

        AuthenticationSucessfull authenticationSucessfull = new AuthenticationSucessfull(token, user);

        response.setContentType("application/json");

        objectMapper.writeValue(response.getWriter(), authenticationSucessfull);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseError standartError = new ResponseError(HttpStatus.UNAUTHORIZED.value(), failed.getMessage(), new Date(System.currentTimeMillis()).getTime());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), standartError);
    }

}
