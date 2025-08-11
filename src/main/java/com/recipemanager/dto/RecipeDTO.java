package com.recipemanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {
    private Long id;

    @NotBlank(message = "Recipe name is required")
    @Size(max = 255, message = "Recipe name must be less than 255 characters")
    private String name;

    @Size(max = 2000, message = "Description must be less than 2000 characters")
    private String description;

    private Long userId; // Owner reference, validated in service if needed

    private List<RecipeIngredientDTO> ingredients;
}
