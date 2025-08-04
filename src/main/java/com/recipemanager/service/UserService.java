package com.recipemanager.service;

import com.recipemanager.model.User;
import com.recipemanager.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

    public User registerNewUser(String userName, String email, String rawPassword) {
        if (userRepository.findByUsername(userName).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = new User();
        user.setUsername(userName);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole("USER"); // default role
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User editUser(Long id, String newUserName, String newEmail, String newPassword, String newRole) {
        User updatedUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        // username update
        if (!newUserName.equals(updatedUser.getUsername()) &&
                userRepository.findByUsername(newUserName).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        updatedUser.setUsername(newUserName);
        // password update
        if (newPassword != null && !newPassword.isBlank()) {
            updatedUser.setPassword(passwordEncoder.encode(newPassword));
        }
        // email update
        if (newEmail != null && !newEmail.isBlank()) {
            if (updatedUser.getEmail().equals(newEmail)) {
                throw new IllegalArgumentException("Email already exists");
            }
            updatedUser.setEmail(newEmail);
        }
        // update role
        if (newRole != null && !newRole.isEmpty()) {
            updatedUser.setRole(newRole);
        }
        return userRepository.save(updatedUser);
    }
}
