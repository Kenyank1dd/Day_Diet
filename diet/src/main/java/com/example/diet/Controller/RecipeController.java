package com.example.diet.Controller;

import com.example.diet.Service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    public static void main(String[] args) {

    }
}
