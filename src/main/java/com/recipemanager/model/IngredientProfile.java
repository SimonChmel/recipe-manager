package com.recipemanager.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IngredientProfile {
    //Nutrition
    private double calories;
    private double protein;
    private double carbs;
    private double fat;
    //Chemical
    private double pH;
    private double moisture;
    private String texture; // creamy, crunchy, fibrous...

    public boolean isSimilar(IngredientProfile alternative, double tolerance) {
        tolerance /= 100;
        boolean nutritionSimilar =
        inTolerance(this.calories, alternative.calories, tolerance) &&
        inTolerance(this.protein, alternative.protein, tolerance) &&
        inTolerance(this.carbs, alternative.carbs, tolerance) &&
        inTolerance(this.fat, alternative.fat,  tolerance);

        boolean chemicallySimilar =
        inTolerance(this.pH, alternative.pH, tolerance) &&
        inTolerance(this.moisture, alternative.moisture, tolerance) &&
        this.texture.equalsIgnoreCase(alternative.texture);

        return nutritionSimilar && chemicallySimilar;
    }

    private boolean inTolerance(double a, double b, double tolerance) {
        if (a == 0 ) return b == 0;
        return Math.abs(a - b) <= Math.abs(a) * tolerance;
    }
}
