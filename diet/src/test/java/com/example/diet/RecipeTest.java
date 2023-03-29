package com.example.diet;

import com.example.diet.Controller.RecipeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

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
        recipeController.updateurl();
    }

    @Test
    void findbynametest() {
        System.out.println(recipeController.findRecipebyName("西红柿炒鸡蛋"));
    }
}
