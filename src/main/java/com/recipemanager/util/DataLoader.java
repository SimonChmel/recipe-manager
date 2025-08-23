package com.recipemanager.util;

import com.recipemanager.model.Ingredient;
import com.recipemanager.model.IngredientProfile;
import com.recipemanager.model.Recipe;
import com.recipemanager.model.RecipeIngredient;
import com.recipemanager.repository.IngredientRepository;
import com.recipemanager.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    @Override
    public void run(String... args) {
        if (ingredientRepository.count() == 0 && recipeRepository.count() == 0) {
            // --- Ingredients ---
            Ingredient milk = new Ingredient("Milk", new IngredientProfile(42, 3.4, 5, 1, 6.7, 87, "creamy"));
            Ingredient soyMilk = new Ingredient("Soy Milk", new IngredientProfile(45, 3.3, 4, 1.8, 7.0, 89, "creamy"));
            Ingredient butter = new Ingredient("Butter", new IngredientProfile(717, 0.85, 0.1, 81, 6.4, 16, "solid"));
            Ingredient margarine = new Ingredient("Margarine", new IngredientProfile(717, 0.5, 0, 80, 6.5, 15, "solid"));
            Ingredient flour = new Ingredient("Flour", new IngredientProfile(364, 10, 76, 1, 6.0, 12, "powder"));
            Ingredient sugar = new Ingredient("Sugar", new IngredientProfile(387, 0, 100, 0, 7.0, 0, "crystalline"));

            ingredientRepository.saveAll(List.of(milk, soyMilk, butter, margarine, flour, sugar));

            // --- Recipe: Pancakes ---
            Recipe pancakes = new Recipe();
            pancakes.setName("Pancakes");
            pancakes.setDescription("Fluffy breakfast pancakes");

            RecipeIngredient pi1 = new RecipeIngredient(pancakes, flour, 200, "grams");
            RecipeIngredient pi2 = new RecipeIngredient(pancakes, milk, 300, "ml");
            RecipeIngredient pi3 = new RecipeIngredient(pancakes, butter, 50, "grams");
            pancakes.setIngredients(List.of(pi1, pi2, pi3));

            // --- Recipe: Toast ---
            Recipe toast = new Recipe();
            toast.setName("Toast");
            toast.setDescription("Simple toast with spread");

            RecipeIngredient ti1 = new RecipeIngredient(toast, butter, 20, "grams");
            RecipeIngredient ti2 = new RecipeIngredient(toast, sugar, 10, "grams");
            toast.setIngredients(List.of(ti1, ti2));

            recipeRepository.saveAll(List.of(pancakes, toast));

            System.out.println("✅ Sample recipes and ingredients loaded into DB.");
        }
    }
}

