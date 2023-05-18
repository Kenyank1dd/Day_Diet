package com.example.diet.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietRecord {
    private List<Recent_diet> break_list;
    private List<Recent_diet> lunch_list;
    private List<Recent_diet> dinner_list;
}
