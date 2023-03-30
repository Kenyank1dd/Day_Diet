package com.example.diet.Service.Impl;

import com.example.diet.Aspect.InvokeLog;
import com.example.diet.Domain.Recipe;
import com.example.diet.Mapper.RecipeMapper;
import com.example.diet.Service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServieImpl implements RecipeService {

    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public void insertRecipe(Recipe recipe) {
        recipeMapper.insertRecipe(recipe);
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        recipeMapper.updateRecipe(recipe);
    }

    @Override
    public Recipe findRecipebyName(String name) {
        return recipeMapper.findRecipebyName(name);
    }

    @Override
    @InvokeLog
    public List<Recipe> SearchRecipe(String searchtxt) {
        return recipeMapper.SearchRecipe(searchtxt);
    }
}
