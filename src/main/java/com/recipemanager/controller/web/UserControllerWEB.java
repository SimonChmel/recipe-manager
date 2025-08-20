package com.recipemanager.controller.web;

import com.recipemanager.dto.UserDTO;
import com.recipemanager.mapper.UserMapper;
import com.recipemanager.model.User;
import com.recipemanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserControllerWEB {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public String getUserList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "list";
    }

    @GetMapping("/user-detail/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        model.addAttribute("id", userService.findById(id));
        return "users/user";
    }

    @GetMapping("/user-detail/{username}")
    public String getUserByUsername(@PathVariable String username, Model model) {
        model.addAttribute("username", userService.findByUsername(username));
        return "users/user";
    }

    @GetMapping("/user-detail/{email}")
    public String getUserByEmail(@PathVariable String email, Model model) {
        model.addAttribute("email", userService.findByEmail(email));
        return "users/user";
    }

    @GetMapping("/create")
    public String registerUserForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "users/form";
    }

    @PostMapping("/create")
    public String registerUser(@Valid @ModelAttribute ("user") UserDTO userDTO,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "users/form";
        }
        userService.registerNewUser(userMapper.toEntity(userDTO));
        redirectAttributes.addFlashAttribute("flash", "User created successfully");
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User not found with id " + id));
        model.addAttribute("user", userMapper.toUserDTO(user));
        return "users/form";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id,
                           @Valid @ModelAttribute ("user") UserDTO userDTO,
                           RedirectAttributes redirectAttributes) {
        userService.editUser(id, userMapper.toEntity(userDTO));
        redirectAttributes.addFlashAttribute("flash", "User updated successfully");
        return "redirect:/users";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id,
                             RedirectAttributes redirectAttributes) {
        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("flash", "User deleted successfully");
        return "redirect:/users";
    }
}
