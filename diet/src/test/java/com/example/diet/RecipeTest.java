package com.example.diet;

import com.example.diet.Controller.RecipeController;
import com.example.diet.Domain.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@SpringBootTest
public class RecipeTest {

    @Autowired
    RecipeController recipeController;

    @Test
    void inserttest() throws IOException {
        recipeController.insertdb();
    }

    @Test
    void updatetest() throws IOException {
        String path = "D:\\Desktop\\OPPO杯\\imgs\\";
        File file = new File(path);
        String[] names = file.list();
        for (String name : names) {
            System.out.println(name);
            File oldName = new File(path + name);
            String newname = UUID.randomUUID().toString().concat(".jpg");
            File newName = new File(path.concat(newname));
            Recipe recipe = new Recipe();
            String[] split = name.split("\\.");
            recipe.setRec_name(split[0]);
            recipe.setRec_url("/root/imgs/" + newname);
            oldName.renameTo(newName);
            recipeController.updateRecipe(recipe);
        }

    }

    @Test
    void findbynametest() {
        System.out.println(recipeController.findRecipebyName("西红柿炒鸡蛋"));
    }
}
