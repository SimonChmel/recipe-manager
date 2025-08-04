package com.recipemanager.controller;

import com.recipemanager.model.Recipe;
import com.recipemanager.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public List<Recipe> getAllRecipes(){
        return recipeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id){
        return recipeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Recipe> saveRecipe(@RequestBody Recipe recipe){
        Recipe newRecipe = recipeService.save(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRecipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe){
        return recipeService.findById(id)
                .map(existing -> {
                    Recipe updatedRecipe = recipeService.save(recipe);
                    return ResponseEntity.ok(updatedRecipe);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id){
        recipeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/replace-missing-ingredient/{missingIngredientId}")
    public ResponseEntity<Recipe> replaceMissingIngredient(@PathVariable Long id, @PathVariable Long missingIngredientId){
        try {
            Recipe updatedRecipe = recipeService.replaceMissingIngredient(missingIngredientId, id);
            return ResponseEntity.ok(updatedRecipe);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/add-ingredient/{ingredientId}")
    public ResponseEntity<Recipe> addIngredient(@PathVariable Long id, @PathVariable Long ingredientId, double quantity, String unit){
        try {
            Recipe updatedRecipe = recipeService.addIngredient(ingredientId, id, quantity, unit);
            return ResponseEntity.ok(updatedRecipe);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}/delete-ingredient/{ingredientId}")
    public ResponseEntity<Recipe> deleteIngredient(@PathVariable Long id, @PathVariable Long ingredientId){
        try {
            Recipe updatedRecipe = recipeService.removeIngredient(id, ingredientId);
            return ResponseEntity.ok(updatedRecipe);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
