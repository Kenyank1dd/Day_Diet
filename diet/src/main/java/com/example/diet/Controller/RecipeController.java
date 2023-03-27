package com.example.diet.Controller;

import com.csvreader.CsvReader;
import com.example.diet.Domain.Recipe;
import com.example.diet.Service.RecipeService;
import com.example.diet.Util.csvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    public void insertRecipe(Recipe recipe) {
        recipeService.insertRecipe(recipe);
    }

    public void insertdb() throws IOException {
        CsvReader csvReader = new CsvReader("src/main/resources/static/final_recipes.csv", ',', StandardCharsets.UTF_8);

        csvReader.readHeaders();
        RecipeController recipeController = new RecipeController();
        // 读取每行的内容
        int cnt = 1;
        while (csvReader.readRecord()) {
            Recipe recipe = new Recipe();
            recipe.setRecName(csvReader.get(0));
            recipe.setRecid(cnt++);
            this.insertRecipe(recipe);
        }
    }
}
