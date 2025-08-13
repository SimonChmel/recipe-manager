package com.recipemanager.service;

import com.recipemanager.model.User;
import com.recipemanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureDataJpa
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void registerNewUser_Success() {
        User user = new User();
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        user.setEmail("testEmail@email.com");

        User result = userService.registerNewUser(user);
        assertNotNull(result.getId());
        assertEquals("testUsername", result.getUsername());
        assertTrue(passwordEncoder.matches("testPassword", result.getPassword()));
        assertEquals("USER", result.getRole());
    }

    @Test
    void registerNewUser_AlreadyExists() {
        User existing = new User();
        existing.setUsername("duplicateUsername");
        existing.setPassword("duplicatePassword");
        existing.setEmail("duplicateEmail@email.com");
        userService.registerNewUser(existing);

        User result = new User();
        result.setUsername("duplicateUsername");
        result.setPassword("resultPassword");
        result.setEmail("result@email.com");
// Verify that the correct exception is thrown with the correct message
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.registerNewUser(result));
        log.info("Exception message for registering new user: {}", exception.getMessage());
        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    void findByUsername_Found() {
        User user = new User();
        user.setUsername("toBeFoundByUsername");
        user.setPassword("testPassword");
        user.setEmail("toBeFoundByUsername@email.com");
        userService.registerNewUser(user);

        Optional<User> result = userService.findByUsername("toBeFoundByUsername");
        assertTrue(result.isPresent());
        assertEquals("toBeFoundByUsername@email.com", result.get().getEmail());
    }

    @Test
    void findByUsername_NotFound() {
        Optional<User> result = userService.findByUsername("toBeFoundByName");
        assertTrue(result.isEmpty());
        log.info("findByUsername returned empty as expected for non-existing user");
    }

    @Test
    void findByEmail_Found() {
        User user = new User();
        user.setUsername("toBeFoundByEmail");
        user.setPassword("testPassword");
        user.setEmail("toBeFoundByEmail@email.com");
        userService.registerNewUser(user);

        Optional<User> result = userService.findByEmail("toBeFoundByEmail@email.com");
        assertTrue(result.isPresent());
        assertEquals("toBeFoundByEmail", result.get().getUsername());
    }

    @Test
    void findByEmail_NotFound() {
        Optional<User> result = userService.findByEmail("toBeFoundByMail@mail.com");
        assertTrue(result.isEmpty());
        log.info("findByEmail returned empty as expected for non-existing user");
    }

    @Test
    void editUser_Success() {
        User oldUser = new User();
        oldUser.setUsername("toBeEdited");
        oldUser.setPassword("testPassword");
        oldUser.setEmail("toBeEdited@email.com");
        oldUser.setRole("USER");
        userService.save(oldUser);

        User updated = new User();
        updated.setUsername("toBeEdited_new");
        updated.setPassword("testPassword_new");
        updated.setRole("ADMIN");

        User edited = userService.editUser(oldUser.getId(), updated);
        assertEquals("toBeEdited_new", edited.getUsername());
        assertTrue(passwordEncoder.matches("testPassword_new", edited.getPassword()));
        assertEquals("ADMIN", edited.getRole());
    }

    @Test
    void editUser_NotFound() {
        User updated = new User();
        updated.setUsername("toBeNotEdited");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.editUser(666L, updated));
        log.info("Exception message for editing a user: {}", exception.getMessage());
        assertEquals("User not found", exception.getMessage());
    }
}