package com.recipemanager.util.mapper;

import com.recipemanager.dto.RecipeDTO;
import com.recipemanager.dto.RecipeIngredientDTO;
import com.recipemanager.model.Recipe;
import com.recipemanager.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RecipeMapper {

    public RecipeDTO toRecipeDTO(Recipe recipe) {
        return new RecipeDTO(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getUser() != null ? recipe.getUser().getId() : null,
                recipe.getIngredients().stream()
                        .map(recipeIngredient -> new RecipeIngredientDTO(
                                recipeIngredient.getIngredient().getId(),
                                recipeIngredient.getIngredient().getName(),
                                recipeIngredient.getQuantity(),
                                recipeIngredient.getUnit()))
                        .toList()
        );
    }

    public Recipe toEntity(RecipeDTO recipeDTO) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeDTO.getId());
        recipe.setName(recipeDTO.getName());
        recipe.setDescription(recipeDTO.getDescription());
        if (recipeDTO.getUserId() != null) {
            User user = new User();
            user.setId(recipeDTO.getUserId());
            recipe.setUser(user);
        }
        // ingredients are added separately through service
        return recipe;
    }
}
