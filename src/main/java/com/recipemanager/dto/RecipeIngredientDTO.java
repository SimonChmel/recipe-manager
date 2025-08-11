package com.recipemanager.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredientDTO {
    @NotNull(message = "Ingredient ID is required")
    private Long ingredientId;

    @NotBlank(message = "Ingredient name is required")
    @Size(max = 255)
    private String ingredientName;

    @Min(value = 0, message = "Quantity must be positive")
    private double quantity;

    @Size(max = 50, message = "Unit must be less than 50 characters")
    private String unit;
}
