package com.recipemanager.repository;

import com.recipemanager.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<Ingredient, Long> {
}
