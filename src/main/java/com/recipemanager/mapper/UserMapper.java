package com.recipemanager.mapper;

import com.recipemanager.dto.UserDTO;
import com.recipemanager.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                null, //password is hidden - never sent it back from mapping
                user.getEmail(),
                user.getRole());
    }

    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        return user;
    }
}
