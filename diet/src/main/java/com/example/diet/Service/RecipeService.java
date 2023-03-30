package com.example.diet.Service;

import com.example.diet.Domain.Recipe;

import java.util.List;

public interface RecipeService {
    public void insertRecipe(Recipe recipe);

    public void updateRecipe(Recipe recipe);

    Recipe findRecipebyName(String name);

    public List<Recipe> SearchRecipe(String searchtxt);
}
