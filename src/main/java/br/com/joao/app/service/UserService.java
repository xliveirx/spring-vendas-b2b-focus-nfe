package br.com.joao.app.service;

import br.com.joao.app.domain.User;
import br.com.joao.app.domain.exception.EmailAlreadyExistsException;
import br.com.joao.app.domain.exception.PasswordsDontMatchException;
import br.com.joao.app.dto.RoleEditRequest;
import br.com.joao.app.dto.UserCreateRequest;
import br.com.joao.app.dto.UserEditRequest;
import br.com.joao.app.dto.UserResponse;
import br.com.joao.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional
    public User createUser(UserCreateRequest req) {

        if(userRepository.findByEmailAndActiveTrue(req.email()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        if(!req.password().equals(req.confirmPassword())){
            throw new PasswordsDontMatchException();
        }

        var encodedPassword = passwordEncoder.encode(req.password());

        return userRepository.save(new User(req.name(), req.email(), encodedPassword));
    }


    @Transactional
    public User editUser(UserEditRequest req, User logged) {

        var user = userRepository.findByEmailAndActiveTrue(logged.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(req.name() != null){
            user.setName(req.name());
        }

        return userRepository.save(user);
    }

    @Transactional
    public void disableUser(User logged) {

        var user = userRepository.findByEmailAndActiveTrue(logged.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setActive(false);

        userRepository.save(user);
    }

    public Page<User> findAll(Pageable pageable) {

        return userRepository.findAll(pageable);
    }

    @Transactional
    public void disableUserById(Long id) {

        var user = userRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setActive(false);

        userRepository.save(user);
    }

    @Transactional
    public void enableUserById(Long id) {

        var user = userRepository.findByIdAndActiveFalse(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setActive(true);

        userRepository.save(user);
    }

    @Transactional
    public void editRole(Long id, RoleEditRequest dto) {

        var user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setRole(dto.role());

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailAndActiveTrue(username).orElseThrow();
    }
}
