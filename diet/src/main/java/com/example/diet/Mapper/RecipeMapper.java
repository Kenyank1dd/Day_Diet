package com.example.diet.Mapper;

import com.example.diet.Domain.Recipe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RecipeMapper {
    public void insertRecipe(Recipe recipe);

    public void updateRecipe(Recipe recipe);

    Recipe findRecipebyName(String name);
}
