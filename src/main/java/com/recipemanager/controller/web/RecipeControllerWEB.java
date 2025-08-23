package com.recipemanager.controller.web;

import com.recipemanager.dto.RecipeDTO;
import com.recipemanager.dto.RecipeIngredientDTO;
import com.recipemanager.model.Ingredient;
import com.recipemanager.model.RecipeIngredient;
import com.recipemanager.util.mapper.RecipeMapper;
import com.recipemanager.model.Recipe;
import com.recipemanager.service.IngredientService;
import com.recipemanager.service.RecipeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recipes")
public class RecipeControllerWEB {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final RecipeMapper recipeMapper;

    @GetMapping
    public String getRecipeList(Model model) {
        model.addAttribute("recipes", recipeService.findAll());
        return "recipes/list";
    }

    @GetMapping("/recipe/{id}")
    public String getRecipeDetails(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findById(id).orElseThrow(EntityNotFoundException::new);
        if (recipe == null) {
            return "redirect:/recipes";
        }
        model.addAttribute("recipe", recipe);

        List<RecipeIngredientDTO> ingredients = recipeService.getIngredientsForRecipe(recipe.getId());
        if (ingredients == null) {
            return "redirect:/recipe";
        }
        model.addAttribute("ingredients", ingredients);
        return "recipes/recipe";
    }

    @GetMapping("/form")
    public String showRecipeForm(@RequestParam(required = false) Long id, Model model) {
        RecipeDTO recipeDTO;
        if (id != null) {
            Recipe editRecipe = recipeService.findById(id).orElseThrow(EntityNotFoundException::new);
            recipeDTO = recipeMapper.toRecipeDTO(editRecipe);
        } else {
            recipeDTO = new RecipeDTO();
        }
        model.addAttribute("recipe", recipeDTO);
        return "recipes/form";
    }

    @PostMapping("/save")
    public String saveRecipe(@Valid @ModelAttribute("recipe") RecipeDTO recipeDTO,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        Optional<Recipe> existingRecipe = recipeService.findByName(recipeDTO.getName());
        if (existingRecipe.isPresent() &&
                (recipeDTO.getId() == null || !existingRecipe.get().getId().equals(recipeDTO.getId()))) {
            result.rejectValue("name", "recipe.name.exists", "Recipe name already exists");
        }
        if (result.hasErrors()) {
            return "recipes/form";
        }
        if (recipeDTO.getId() == null) {
            recipeService.save(recipeMapper.toEntity(recipeDTO));
            redirectAttributes.addFlashAttribute("flash", "Recipe saved successfully");
        } else {
            recipeService.editRecipe(recipeDTO.getId(), recipeMapper.toEntity(recipeDTO));
            redirectAttributes.addFlashAttribute("message", "Recipe edited successfully");
        }
        return "redirect:/recipes";
    }

    @PostMapping("/edit/{id}")
    public String editRecipe(@PathVariable Long id, @Valid RecipeDTO recipeDTO, BindingResult result) {
        Optional<Recipe> existingRecipe = recipeService.findByName(recipeDTO.getName());
        if (existingRecipe.isPresent() && !existingRecipe.get().getId().equals(id)) {
            result.rejectValue("name", "recipe.name.exists", "Recipe name already exists");
        }
        if (result.hasErrors()) {
            return "recipes/form";
        }
        recipeService.editRecipe(id, recipeMapper.toEntity(recipeDTO));
        return "redirect:/recipes";
    }

    @GetMapping("/delete/{id}")
    public String deleteRecipeConfirm(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findById(id).orElse(null);
        if (recipe == null) {
            return "redirect:/recipes";
        }
        model.addAttribute("recipe", recipe);
        return "recipes/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        recipeService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Recipe deleted successfully");
        return "redirect:/recipes";
    }

    @GetMapping("/ingredient")
    public String showIngredientForm(@RequestParam(required = false) Long recipeId, Model model) {

        model.addAttribute("ingredient", new RecipeIngredientDTO());
        model.addAttribute("recipeId", recipeId);
        return "recipes/form";
    }

    @PostMapping("/ingredients/add/{id}")
    public String addIngredient(@PathVariable Long id, @ModelAttribute RecipeIngredientDTO ingredientDTO, RedirectAttributes redirectAttributes) {
        recipeService.addIngredient(id, ingredientDTO.getIngredientId(), ingredientDTO.getQuantity(), ingredientDTO.getUnit());
        redirectAttributes.addFlashAttribute("message", "Ingredient added successfully");
        return "redirect:/recipes/form";
    }

    @DeleteMapping("/{recipeId}/ingredients/delete/{ingredientId}")
    public String deleteIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId, RedirectAttributes redirectAttributes) {
        recipeService.removeIngredient(recipeId, ingredientId);
        redirectAttributes.addFlashAttribute("message", "Ingredient deleted successfully");
        return "redirect:/recipes/edit/" + recipeId;
    }
}

