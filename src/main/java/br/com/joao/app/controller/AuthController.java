package br.com.joao.app.controller;

import br.com.joao.app.domain.User;
import br.com.joao.app.dto.LoginRequest;
import br.com.joao.app.dto.LoginResponse;
import br.com.joao.app.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest req){

        var authentication = new UsernamePasswordAuthenticationToken(req.email(), req.password());
        var authenticate = this.authenticationManager.authenticate(authentication);

        String token = this.tokenService.generateToken((User) authenticate.getPrincipal());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
