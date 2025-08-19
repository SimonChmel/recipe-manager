package com.recipemanager.service;

import com.recipemanager.model.Ingredient;
import com.recipemanager.model.Recipe;
import com.recipemanager.model.RecipeIngredient;
import com.recipemanager.repository.IngredientRepository;
import com.recipemanager.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureDataJpa
class RecipeServiceTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    private RecipeService recipeService;
    private IngredientService ingredientService;

    private static final Logger log = LoggerFactory.getLogger(RecipeServiceTest.class);

    @BeforeEach
    void setUp() {
        recipeService = new RecipeService(recipeRepository, ingredientRepository, ingredientService);
        ingredientService = new IngredientService(ingredientRepository);
    }

    private Recipe createRecipe(String name) {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setIngredients(new ArrayList<>());
        return recipeService.save(recipe);
    }

    private Ingredient createIngredient(String name) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        return ingredientService.save(ingredient);
    }

    private RecipeIngredient createRecipeIngredient(Recipe recipe, Ingredient ingredient, double quantity, String unit) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setUnit(unit);
        recipeIngredient.setQuantity(quantity);
        recipe.getIngredients().add(recipeIngredient);
        return recipeIngredient;
    }

    @Test
    void addIngredient_Success() {
        Recipe recipe = createRecipe("testRecipe");
        Ingredient ingredient = createIngredient("testIngredient");

        Recipe updatedRecipe = recipeService.addIngredient(ingredient.getId(), ingredient.getId(), 100, "grams");
        assertEquals(1, updatedRecipe.getIngredients().size());
        RecipeIngredient recipeIngredient = updatedRecipe.getIngredients().get(0);
        assertEquals("testIngredient", recipeIngredient.getIngredient().getName());
        assertEquals(100, recipeIngredient.getQuantity());
        assertEquals("grams", recipeIngredient.getUnit());
    }

    @Test
    void addIngredient_RecipeNotFound() {
        Ingredient ingredient = createIngredient("testIngredient");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> recipeService.addIngredient(999L, ingredient.getId(), 100, "grams")
        );
        log.info("Exception message for adding new ingredient to missing recipe: {}", exception.getMessage());
    }

    @Test
    void addIngredient_IngredientNotFound() {
        Recipe recipe = createRecipe("testRecipe");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> recipeService.addIngredient(recipe.getId(), 999L, 100, "grams")
        );
        log.info("Exception message for adding new ingredient when it is missing: {}", exception.getMessage());
    }
}