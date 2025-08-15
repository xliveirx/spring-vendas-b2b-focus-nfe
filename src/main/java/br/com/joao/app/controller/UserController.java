package br.com.joao.app.controller;

import br.com.joao.app.domain.Role;
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
import java.util.List;

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

        return ResponseEntity.created(URI.create("/users/" + req.name())).body(body);

    }

    @PutMapping("/edit-profile")
    public ResponseEntity<UserResponse> editUser(@RequestBody UserEditRequest req,
                                                 @AuthenticationPrincipal User logged) {

        var body = this.userService.editUser(req, logged);

        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/disable")
    public ResponseEntity<Void> disableUser(@AuthenticationPrincipal User logged) {

        this.userService.disableUser(logged);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        var users = this.userService.findAll(pageable);

        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/disable/{id}")
    public ResponseEntity<Void> disableUser(@PathVariable Long id) {

        this.userService.disableUserById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/enable/{id}")
    public ResponseEntity<Void> enableUser(@PathVariable Long id) {

        this.userService.enableUserById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/edit-role/{id}")
    public ResponseEntity<Void> editRole(@PathVariable Long id, @RequestBody RoleEditRequest role) {

        this.userService.editRole(id, role);

        return ResponseEntity.noContent().build();

    }

}
