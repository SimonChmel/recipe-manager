package com.recipemanager.controller;

import com.recipemanager.dto.RecipeDTO;
import com.recipemanager.dto.RecipeIngredientDTO;
import com.recipemanager.mapper.RecipeMapper;
import com.recipemanager.model.Recipe;
import com.recipemanager.service.RecipeService;
import jakarta.validation.Valid;
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
    private final RecipeMapper recipeMapper;

    @GetMapping
    public List<RecipeDTO> getAllRecipes(){
        return recipeService.findAll()
                .stream()
                .map(recipeMapper::toRecipeDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable Long id){
        return recipeService.findById(id)
                .map(recipeMapper::toRecipeDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RecipeDTO> saveRecipe(@Valid @RequestBody RecipeDTO recipeDTO){
        Recipe newRecipe = recipeService.save(recipeMapper.toEntity(recipeDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeMapper.toRecipeDTO(newRecipe));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable Long id, @Valid @RequestBody RecipeDTO recipeDTO){
        return recipeService.findById(id)
                .map(existing -> {
                    Recipe updatedRecipe = recipeService.save(recipeMapper.toEntity(recipeDTO));
                    updatedRecipe.setId(id);
                    return ResponseEntity.ok(recipeMapper.toRecipeDTO(updatedRecipe));
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

    @GetMapping("/{id}/ingredients")
    public ResponseEntity<List<RecipeIngredientDTO>> getAllRecipeIngredients(@PathVariable Long id){
        try{
            return ResponseEntity.ok(recipeService.getIngredientsForRecipe(id));
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }
}
