package com.recipemanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private IngredientProfile profile;

    @Column(length = 2000)
    private String chemicalProfileJson;
    // Bidirectional mapping to RecipeIngredient
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private List<RecipeIngredient> recipes = new ArrayList<>();

    // Convenience constructor for DataLoader (without id)
    public Ingredient(String name, IngredientProfile profile) {
        this.name = name;
        this.profile = profile;
    }
}
