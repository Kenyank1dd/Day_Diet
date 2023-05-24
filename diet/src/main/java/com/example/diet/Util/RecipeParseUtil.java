package com.example.diet.Util;

import java.util.*;
import java.util.stream.IntStream;

public class RecipeParseUtil {
    public static Map<String,Object> RecipeParse(String result) {
        Map<String,Object> data = new HashMap<>();
        List<String> material = new ArrayList<>();
        List<String> step = new ArrayList<>();
        List<String> tips = new ArrayList<>();

        List<String> res_split_enter = Arrays.asList(result.split("\n"));

        String recipe_name = res_split_enter.get(0).substring(res_split_enter.get(0).indexOf('：')+1);
        for(int i = 0; i < res_split_enter.size(); i++) {
            while(res_split_enter.get(i).length() == 0) i++;
            if(res_split_enter.get(i).contains("食材：")) {
                while(res_split_enter.get(i+1).length() == 0) i++;
                while(res_split_enter.get(++i).contains("-") || res_split_enter.get(i).contains(".")) {
                    material.add(res_split_enter.get(i).substring(res_split_enter.get(i).indexOf(' ')+1));
                }
            }
            if(res_split_enter.get(i).contains("制作步骤：")) {
                while(res_split_enter.get(i+1).length() == 0) i++;
                while(res_split_enter.get(++i).contains(".")) {
                    step.add(res_split_enter.get(i).substring(res_split_enter.get(i).indexOf(' ')+1));
                }
            }
            if(res_split_enter.get(i).contains("小贴士：")) {
                while(res_split_enter.get(i+1).length() == 0) i++;
                while(res_split_enter.get(++i).contains(".")) {
                    tips.add(res_split_enter.get(i).substring(res_split_enter.get(i).indexOf(' ')+1));
                    if(i == res_split_enter.size() - 1) break;
                }
            }
        }

        data.put("name",recipe_name);
        data.put("material",material);
        data.put("step",step);
        data.put("tips",tips);
        return data;
    }
}
