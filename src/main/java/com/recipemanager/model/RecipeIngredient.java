/*
Why do we need RecipeIngredient?
A Recipe contains multiple ingredients.
An Ingredient can appear in multiple recipes.
In a normal many-to-many mapping, we’d just have a join table recipe_ingredients(recipe_id, ingredient_id).
BUT: We also need extra data about the relationship:
How much of the ingredient (quantity)
In which unit (grams, cups, tablespoons, etc.)
JPA can’t store those extra fields in a plain many-to-many table, so we make RecipeIngredient an explicit entity.
 */

package com.recipemanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    private double quantity;

    private String unit;

    // Convenience constructor for DataLoader (without id)
    public RecipeIngredient(Recipe recipe, Ingredient ingredient, int i, String unit) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.quantity = i;
        this.unit = unit;
    }
}
