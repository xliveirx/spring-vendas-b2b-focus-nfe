package br.com.joao.app.controller;

import br.com.joao.app.dto.UserCreateRequest;
import br.com.joao.app.dto.UserResponse;
import br.com.joao.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserCreateRequest req) {

        var body = this.userService.createUser(req);

        return ResponseEntity.ok(body);

    }

}
