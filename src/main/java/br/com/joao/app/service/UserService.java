package br.com.joao.app.service;

import br.com.joao.app.domain.User;
import br.com.joao.app.domain.exception.EmailAlreadyExistsException;
import br.com.joao.app.domain.exception.PasswordsDontMatchException;
import br.com.joao.app.dto.UserCreateRequest;
import br.com.joao.app.dto.UserResponse;
import br.com.joao.app.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(UserCreateRequest req) {

        if(userRepository.findByEmail(req.email()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        if(!req.password().equals(req.confirmPassword())){
            throw new PasswordsDontMatchException();
        }

        var encodedPassword = passwordEncoder.encode(req.password());

        var user = userRepository.save(new User(req.name(), req.email(), encodedPassword));

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getUsername()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow();
    }
}
