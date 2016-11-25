package jac.cookhelper;

import android.net.Uri;

import java.io.Serializable;



public class RecipeData implements Serializable {
    private String recipeName;
    private String recipeDescription;
    private int cookingTime;
    private int numServings;
    private String[] ingredients;
    private String[] stepsDescriptions;
    private Uri[] stepsImages;

    public RecipeData(String recipeName, String recipeDescription,
                      int cookingTime, int numServings,
                      String[] ingredients, String[] stepsDescriptions,
                      Uri[] stepsImages) {

        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.cookingTime = cookingTime;
        this.numServings = numServings;
        this.ingredients = ingredients;
        this.stepsDescriptions = stepsDescriptions;
        this.stepsImages = stepsImages;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public int getNumServings() {
        return numServings;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public String[] getStepsDescriptions() {
        return stepsDescriptions;
    }

    public Uri[] getStepsImages() {
        return stepsImages;
    }
}
