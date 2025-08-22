package com.recipemanager.controller.web;

import com.recipemanager.dto.UserDTO;
import com.recipemanager.mapper.UserMapper;
import com.recipemanager.model.User;
import com.recipemanager.service.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserControllerWEB {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public String getUserList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/list";
    }

    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        User user = userService.findById(id).orElseThrow(EntityNotFoundException::new);
        if (user == null) {
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "users/user";
    }

//    @GetMapping("/user/{username}")
//    public String getUserByUsername(@PathVariable String username, Model model) {
//        User user = userService.findByUsername(username).orElseThrow(EntityNotFoundException::new);
//        if (user == null) {
//            return "redirect:/users";
//        }
//        model.addAttribute("user", user);
//        return "users/user";
//    }

    @GetMapping("/form")
    public String showUserForm(@RequestParam(required = false) Long id, Model model) {
        UserDTO userDTO;
        if (id != null) {
            User editUser = userService.findById(id).orElseThrow(EntityExistsException::new);
            userDTO = userMapper.toUserDTO(editUser);
        } else {
            userDTO = new UserDTO();
        }
        model.addAttribute("user", userDTO);
        model.addAttribute("roles", Arrays.asList("USER", "ADMIN"));
        return "users/form";
    }

    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("user") UserDTO userDTO,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        // Check username uniqueness
        Optional<User> existingUsername = userService.findByUsername(userDTO.getUsername());
        if (existingUsername.isPresent() &&
                (userDTO.getId() == null || !existingUsername.get().getId().equals(userDTO.getId()))) {
            result.rejectValue("username", "user.username.exists", "Username already exists");
        }
        // Check email uniqueness
        Optional<User> existingEmail = userService.findByEmail(userDTO.getEmail());
        if (existingEmail.isPresent() &&
                (userDTO.getId() == null || !existingEmail.get().getId().equals(userDTO.getId()))) {
            result.rejectValue("email", "user.email.exists", "Email already exists");
        }
        if (result.hasErrors()) {
            return "users/form";
        }
        if (userDTO.getId() == null) {
            userService.registerNewUser(userMapper.toEntity(userDTO));
            redirectAttributes.addFlashAttribute("flash", "User created successfully");
        } else {
            userService.editUser(userDTO.getId(), userMapper.toEntity(userDTO));
            redirectAttributes.addFlashAttribute("flash", "User updated successfully");
        }
        return "redirect:/users";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, @Valid UserDTO userDTO, BindingResult result) {
        Optional<User> existing = userService.findByUsername(userDTO.getUsername());
        if (existing.isPresent() && !existing.get().getId().equals(id)) {
            result.rejectValue("username", "user.username.exists", "Username already exists");
        }
        if (result.hasErrors()) {
            return "users/form";
        }
        userService.editUser(id, userMapper.toEntity(userDTO));
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUserConfirm(@PathVariable Long id, Model model) {
        User user = userService.findById(id).orElse(null);
        if (user == null) {
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "users/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("flash", "User deleted successfully");
        return "redirect:/users";
    }
}
