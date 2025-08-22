package com.recipemanager.service;

import com.recipemanager.model.User;
import com.recipemanager.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService extends BaseService<User, Long> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected JpaRepository<User, Long> getJpaRepository() {
        return userRepository;
    }

    public User registerNewUser(@NotNull User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("USER");
        }
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User editUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(existing -> {
                    if (updatedUser.getUsername() != null && !updatedUser.getUsername().isBlank()) {
                        existing.setUsername(updatedUser.getUsername());
                    }
                    if (updatedUser.getEmail() != null && !updatedUser.getEmail().isBlank()) {
                        existing.setEmail(updatedUser.getEmail());
                    }
                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
                        existing.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }
                    if (updatedUser.getRole() != null && !updatedUser.getRole().isBlank()) {
                        existing.setRole(updatedUser.getRole());
                    }
                    return userRepository.save(existing);
                }).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
