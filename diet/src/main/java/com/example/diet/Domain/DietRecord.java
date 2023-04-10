package com.example.diet.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietRecord {
    private List<Recipe> break_list;
    private List<Recipe> lunch_list;
    private List<Recipe> dinner_list;
}
