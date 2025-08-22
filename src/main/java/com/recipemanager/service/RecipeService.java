package com.recipemanager.service;

import com.recipemanager.dto.RecipeIngredientDTO;
import com.recipemanager.model.Ingredient;
import com.recipemanager.model.Recipe;
import com.recipemanager.model.RecipeIngredient;
import com.recipemanager.repository.IngredientRepository;
import com.recipemanager.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeService extends BaseService<Recipe, Long> {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientService ingredientService; // for replacement of ingredient

    @Override
    protected JpaRepository<Recipe, Long> getJpaRepository() {
        return recipeRepository;
    }

    public Recipe editRecipe(Long id, Recipe editRecipe) {
        return recipeRepository.findById(id)
                .map(existing -> {
                    if (editRecipe.getName() != null && !editRecipe.getName().isBlank()) {
                        existing.setName(editRecipe.getName());
                    }
                    if (editRecipe.getDescription() != null && !editRecipe.getDescription().isBlank()) {
                        existing.setDescription(editRecipe.getDescription());
                    }
                    if (editRecipe.getIngredients() != null && !editRecipe.getIngredients().isEmpty()) {
                        existing.setIngredients(editRecipe.getIngredients());
                    }
                    return recipeRepository.save(existing);
                }).orElseThrow(() -> new IllegalArgumentException("Recipe not found"));
    }

    public Recipe addIngredient(Long recipeId, Long ingredientId, double quantity, String unit) {
        Recipe recipe = recipeRepository
                .findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

        Ingredient ingredient = ingredientRepository
                .findById(ingredientId)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found"));

        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setQuantity(quantity);
        recipeIngredient.setUnit(unit);

        recipe.getIngredients().add(recipeIngredient);
        return recipeRepository.save(recipe);
    }

    public Recipe replaceMissingIngredient (Long recipeId, Long missingIngredientId) {
        Recipe recipe = recipeRepository
                .findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe with id " + recipeId + " not found"));

        Ingredient missingIngredient = ingredientRepository
                .findById(missingIngredientId)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient with id " + missingIngredientId + " not found"));

        Ingredient alternativeIngredient = ingredientService
                .findAlternative(missingIngredient)
                .orElseThrow(() -> new IllegalArgumentException("No suitable alternative found"));

        recipe.getIngredients().forEach((ingredient) -> {
            if (ingredient.getIngredient().equals(missingIngredient)) {
                ingredient.setIngredient(alternativeIngredient);
            }
        });
        return recipeRepository.save(recipe);
    }

    public Recipe removeIngredient(Long recipeId, Long ingredientId) {
        Recipe recipe = recipeRepository
                .findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe with id " + recipeId + " not found"));

        recipe.getIngredients().removeIf(recipeIngredient -> recipeIngredient.getId().equals(ingredientId));
        return recipeRepository.save(recipe);
    }

    public Optional<Recipe> findByName(String name){
        return recipeRepository.findByName(name);
    }

    public List<RecipeIngredientDTO> getIngredientsForRecipe(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe with id " + recipeId + " not found"));

        return recipe.getIngredients().stream()
                .map(recipeIngredient -> new RecipeIngredientDTO(
                        recipeIngredient.getIngredient().getId(),
                        recipeIngredient.getIngredient().getName(),
                        recipeIngredient.getQuantity(),
                        recipeIngredient.getUnit()))
                .toList();
    }
}
