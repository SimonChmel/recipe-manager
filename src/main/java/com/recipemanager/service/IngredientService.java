package com.recipemanager.service;

import com.recipemanager.model.Ingredient;
import com.recipemanager.repository.IngredientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientService extends BaseService<Ingredient, Long> {

    private final IngredientRepository ingredientRepository;

    @Override
    protected JpaRepository<Ingredient, Long> getJpaRepository() {
        return ingredientRepository;
    }

    public Optional<Ingredient> findAlternative(Ingredient ingredient){
        // placeholder for possible AI
        return ingredientRepository
                .findAll()
                .stream()
                .filter(ingr -> !ingr.getId().equals(ingredient.getId())) // exclude itself
                .filter(ingr -> ingr.getProfile().isSimilar(ingredient.getProfile(), 15))
                .findFirst();
    }
}
