package com.recipemanager.controller.web;

import com.recipemanager.dto.RecipeDTO;
import com.recipemanager.dto.RecipeIngredientDTO;
import com.recipemanager.mapper.RecipeMapper;
import com.recipemanager.model.Recipe;
import com.recipemanager.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/recipes")
public class RecipeControllerWEB {

    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;

    @GetMapping
    public String getRecipeList(Model model) {
        model.addAttribute("recipes", recipeService.findAll());
        return "recipes/list";
    }

    @GetMapping("/recipe-detail/{id}")
    public String getRecipeDetails(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));
        return "recipes/recipe";
    }

    @GetMapping("/create")
    public String createRecipeForm(Model model) {
        model.addAttribute("recipe", new RecipeDTO());
        return "recipes/form";
    }

    @PostMapping("/create")
    public String createRecipe(@Valid @ModelAttribute ("recipe") RecipeDTO recipeDTO,
                               RedirectAttributes redirectAttributes,
                               BindingResult result,
                               RedirectAttributes redirectAttribute) {
        if (result.hasErrors()) {
            return "recipes/form";
        }
        recipeService.save(recipeMapper.toEntity(recipeDTO));
        redirectAttribute.addFlashAttribute("flash", "Recipe created successfully");
        return "redirect:/recipes";
    }

    @GetMapping("/edit/{id}")
    public String editRecipeForm(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + id));
        model.addAttribute("recipe", recipeMapper.toRecipeDTO(recipe));
        return "recipes/form";
    }

    @PostMapping("/edit/{id}")
    public String editRecipe(@PathVariable Long id, @ModelAttribute ("recipe") RecipeDTO recipeDTO, RedirectAttributes redirectAttributes) {
        if (recipeService.findById(id).isPresent()) {
            recipeService.save(recipeMapper.toEntity(recipeDTO));
        }
        redirectAttributes.addFlashAttribute("flash", "Recipe updated successfully");
        return "redirect:/recipes";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRecipeForm(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        recipeService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Recipe deleted successfully");
        return "redirect:/recipes";
    }

    @GetMapping("/{id}/ingredients/add")
    public String addIngredientForm(@PathVariable Long id, Model model) {
        model.addAttribute("ingredient", new RecipeIngredientDTO());
        model.addAttribute("recipeId", id);
        return "recipes/add-ingredients";
    }

    @PostMapping("/{id}/ingredients/add")
    public String addIngredient(@PathVariable Long id, @ModelAttribute RecipeIngredientDTO ingredientDTO, RedirectAttributes redirectAttributes) {
        recipeService.addIngredient(id, ingredientDTO.getIngredientId(), ingredientDTO.getQuantity(), ingredientDTO.getUnit());
        redirectAttributes.addFlashAttribute("message", "Ingredient added successfully");
        return "redirect:/recipes/edit/" + id;
    }

    @DeleteMapping("/{recipeId}/ingredients/delete/{ingredientId}")
    public String deleteIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId, RedirectAttributes redirectAttributes) {
        recipeService.removeIngredient(recipeId, ingredientId);
        redirectAttributes.addFlashAttribute("message", "Ingredient deleted successfully");
        return "redirect:/recipes/edit/" + recipeId;
    }
}

