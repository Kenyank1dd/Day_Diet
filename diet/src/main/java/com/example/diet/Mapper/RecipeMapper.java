package com.example.diet.Mapper;

import com.example.diet.Domain.Recipe;
import com.example.diet.Domain.Request;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RecipeMapper {
    public void insertRecipe(Recipe recipe);

    public void updateRecipe(Recipe recipe);

    public Recipe findRecipebyName(String name);

    public List<Recipe> SearchRecipe(String seachtxt);

    List<Recipe> RecipeRank();

}
