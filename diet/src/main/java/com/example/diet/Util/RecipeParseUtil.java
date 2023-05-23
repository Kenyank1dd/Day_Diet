package com.example.diet.Util;

import java.util.*;

public class RecipeParseUtil {
    public static Map<String,Object> RecipeParse(String result) {
        Map<String,Object> data = new HashMap<>();
        if(result.indexOf('-') == -1) {

        }
        else {
            List<String> res_split_enter = Arrays.asList(result.split("\n"));
            List<String> material = new ArrayList<>();
            List<String> step = new ArrayList<>();
            String tips;
            for(String t : res_split_enter) {
                if(t.contains("-")) {
                    material.add(t.substring(t.indexOf('-')+1).trim());
                }
                else if(t.contains(".")) {
                    step.add(t.substring(t.indexOf('.')+1).trim());
                }
            }
            tips = res_split_enter.get(res_split_enter.size()-1);

            data.put("material",material);
            data.put("step",step);
            data.put("tips",tips);
        }
        return data;
    }
}
