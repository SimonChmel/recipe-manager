package com.recipemanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne//(fetch = FetchType.LAZY)
    //@JoinColumn(name = "user_id")
    private User user;
    // Bidirectional mapping to RecipeIngredient
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeIngredient> ingredients = new ArrayList<>();

//    // Helper method to add ingredient
//    public void addIngredient(Ingredient ingredient, double quantity, String unit) {
//        RecipeIngredient ri = new RecipeIngredient(this, ingredient, quantity, unit);
//        ingredients.add(ri);
//        ingredient.getRecipes().add(ri);
//    }
//
//    // Helper method to remove ingredient
//    public void removeIngredient(Ingredient ingredient) {
//        ingredients.removeIf(ri -> ri.getIngredient().equals(ingredient));
//        ingredient.getRecipes().removeIf(ri -> ri.getRecipe().equals(this));
//    }
}
