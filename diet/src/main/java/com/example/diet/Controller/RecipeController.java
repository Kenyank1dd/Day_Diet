package com.example.diet.Controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.csvreader.CsvReader;
import com.example.diet.Domain.Recipe;
import com.example.diet.Domain.ResponseResult;
import com.example.diet.Service.RecipeService;
import com.example.diet.Util.TokenUtil;
import com.example.diet.Util.csvUtil;
import com.google.gson.Gson;
import okhttp3.RequestBody;
import org.springframework.web.bind.annotation.*;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    public static final String API_KEY = "9Olsvwarb7N2c244RMNU50bn";
    public static final String SECRET_KEY = "6Gf7Lj4kMOKsetQjmvr6Pv0rWqBAG7lG";
    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

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
        System.out.println(searchtxt);
        List<Recipe> recipes = recipeService.SearchRecipe(searchtxt);
        for(Recipe recipe : recipes) {
            System.out.println(recipe);
        }
        System.out.println("Inquiry complete successfully!");
        return new ResponseResult(200,recipes);
    }

    @GetMapping("/rank/recipe")
    public ResponseResult RecipeRank() {
        List<Recipe> recipes = recipeService.RecipeRank();
        for(Recipe recipe : recipes) {
            System.out.println(recipe);
        }
        System.out.println("Recipe ranking search complete successfully!");
        return new ResponseResult(200,recipes);
    }

    @PostMapping("/identify/recipe")
    public ResponseResult RecipeIdentify(@org.springframework.web.bind.annotation.RequestBody Map map) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String picbase = (String) map.get("imageBase64");
        String imgParam = URLEncoder.encode(picbase, "UTF-8");

        RequestBody body = RequestBody.create(mediaType, "image=" + imgParam);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/image-classify/v2/dish?access_token=" + TokenUtil.getAccessToken(API_KEY,SECRET_KEY))
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject(response.body().string());
        JSONArray resultArray = jsonObject.getJSONArray("result");
        System.out.println(resultArray);
        Map firstFood = gson.fromJson(resultArray.getJSONObject(0).toString(), Map.class);
        System.out.println(firstFood.toString());
        return new ResponseResult(200, "识别成功", firstFood);
    }

}
