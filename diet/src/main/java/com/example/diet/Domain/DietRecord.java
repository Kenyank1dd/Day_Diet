package com.example.diet.Domain;

import java.util.List;

public class DietRecord {
    private List<Recipe> break_list;
    private List<Recipe> lunch_list;
    private List<Recipe> dinner_list;

    public DietRecord(List<Recipe> break_list, List<Recipe> lunch_list, List<Recipe> dinner_list) {
        this.break_list = break_list;
        this.lunch_list = lunch_list;
        this.dinner_list = dinner_list;
    }
}
