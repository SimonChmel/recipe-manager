package com.recipemanager.controller.api;

import com.recipemanager.model.Ingredient;
import com.recipemanager.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    public List<Ingredient> getAllIngredients(){
        return ingredientService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Long id){
        return ingredientService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient){
        Ingredient newIngredient = ingredientService.save(ingredient);
        return ResponseEntity.status(HttpStatus.CREATED).body(newIngredient);
    }

    @PutMapping("{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient){
        return ingredientService.findById(id)
                .map(existing -> {
                    Ingredient updatedIngredient = ingredientService.save(ingredient);
                    return ResponseEntity.ok(updatedIngredient);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id){
        ingredientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
