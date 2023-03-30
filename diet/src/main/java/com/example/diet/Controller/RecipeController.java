package com.example.diet.Controller;

import com.csvreader.CsvReader;
import com.example.diet.Domain.Recipe;
import com.example.diet.Domain.ResponseResult;
import com.example.diet.Service.RecipeService;
import com.example.diet.Util.csvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    public void insertRecipe(Recipe recipe) {
        recipeService.insertRecipe(recipe);
    }

    public void updateRecipe(Recipe recipe) { recipeService.updateRecipe(recipe); }

    public Recipe findRecipebyName(String name) {return recipeService.findRecipebyName(name);}

    public void insertdb() throws IOException {
        CsvReader csvReader = new CsvReader("src/main/resources/static/final_recipes.csv", ',', StandardCharsets.UTF_8);

        csvReader.readHeaders();
        // 读取每行的内容
        int cnt = 1;
        while (csvReader.readRecord()) {
            Recipe recipe = new Recipe();
            recipe.setRec_name(csvReader.get(0));
            recipe.setRec_id(cnt++);
            this.insertRecipe(recipe);
        }
    }

    public void updateurl() throws IOException {
        CsvReader csvReader = new CsvReader("src/main/resources/static/final_recipes.csv", ',', StandardCharsets.UTF_8);

        csvReader.readHeaders();
        while(csvReader.readRecord()){
            Recipe recipe = new Recipe();
            recipe.setRec_name(csvReader.get(0));
            recipe.setRec_url("/root/imgs/" + csvReader.get(0) + ".jpg");
            this.updateRecipe(recipe);
        }
    }

    @GetMapping("/search/recipe")
    public ResponseResult SearchRecipe(@RequestParam(value = "searchtxt") String searchtxt) {
        List<Recipe> recipes = recipeService.SearchRecipe(searchtxt);
        return new ResponseResult(200,recipes);
    }

    @GetMapping("/rank/recipe")
    public ResponseResult RecipeRank() {
        List<Recipe> recipes = recipeService.RecipeRank();
        return new ResponseResult(200,recipes);
    }
}
