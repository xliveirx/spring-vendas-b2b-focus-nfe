package br.com.joao.app.controller;

import br.com.joao.app.domain.User;
import br.com.joao.app.dto.RoleEditRequest;
import br.com.joao.app.dto.UserCreateRequest;
import br.com.joao.app.dto.UserEditRequest;
import br.com.joao.app.dto.UserResponse;
import br.com.joao.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserCreateRequest req) {

        var user = userService.createUser(req);

        var res = UserResponse.fromDomain(user);

        return ResponseEntity.created(URI.create("/users/" + req.name())).body(res);

    }

    @PutMapping("/edit-profile")
    public ResponseEntity<UserResponse> editUser(@RequestBody UserEditRequest req,
                                                 @AuthenticationPrincipal User logged) {

        var user = userService.editUser(req, logged);

        var res = UserResponse.fromDomain(user);

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/disable")
    public ResponseEntity<Void> disableUser(@AuthenticationPrincipal User logged) {

        userService.disableUser(logged);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        var users = userService.findAll(pageable);

        var res = users
                .map(UserResponse::fromDomain);

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/disable/{id}")
    public ResponseEntity<Void> disableUser(@PathVariable Long id) {

        userService.disableUserById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/enable/{id}")
    public ResponseEntity<Void> enableUser(@PathVariable Long id) {

        userService.enableUserById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/edit-role/{id}")
    public ResponseEntity<Void> editRole(@PathVariable Long id, @RequestBody RoleEditRequest role) {

        userService.editRole(id, role);

        return ResponseEntity.noContent().build();

    }
}
