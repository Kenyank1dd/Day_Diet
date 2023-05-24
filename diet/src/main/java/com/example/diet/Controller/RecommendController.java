package com.example.diet.Controller;

import com.example.diet.Domain.FamilyInfo;
import com.example.diet.Domain.Recipe;
import com.example.diet.Domain.ResponseResult;
import com.example.diet.Domain.Settings;
import com.example.diet.Resolver.CurrentUserId;
import com.example.diet.Service.RecipeService;
import com.example.diet.Service.RecordService;
import com.example.diet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/api/v1/recommend")
public class RecommendController {

    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecordService recordService;

    @PostMapping("/recipe")
    public ResponseResult recommend(@RequestBody String[] recipe, @CurrentUserId String userid, @RequestParam (value = "number_recipes") Integer number_recipes) throws IOException, InterruptedException {
        //   加一个参数获取的过程

        List<String> argstemp = new ArrayList<>();
        argstemp.add("C:\\Users\\KK\\.conda\\envs\\pytorch\\python.exe");
//        argstemp.add("/root/anaconda3/envs/KK/bin/python");
        argstemp.add("src/main/resources/static/daydietrecommend/main.py");
//        argstemp.add("D:\\Desktop\\OPPOcode\\Day_Diet\\diet\\src\\main\\resources\\static\\daydietrecommend\\main.py");
//        argstemp.add("/root/daydietrecommend/main.py");
        for (String s : recipe) {
            System.out.println(s);
        }
        List<Map> familyInfos = userService.findFamilyInfoByusrId(userid);
        for(Map familyInfo : familyInfos) {
            System.out.println(familyInfo);
        }
        argstemp.add(String.valueOf(number_recipes));
        Settings settings = recordService.getSettings(userid);
        argstemp.add(String.valueOf(settings.getYingyangjiegou()));
        argstemp.add(String.valueOf(settings.getShicaiqihe()));
        argstemp.add(String.valueOf(settings.getTeshuxuqiu()));
        argstemp.add(String.valueOf(settings.getJijieshiling()));
        argstemp.add(String.valueOf(settings.getYinshipianhao()));

        argstemp.add(String.valueOf(familyInfos.size()));
        argstemp.add(String.valueOf(recipe.length));
        Collections.addAll(argstemp, recipe);
        for (Map familyInfo : familyInfos) {
            List<Double> nutri = (List<Double>) familyInfo.get("nutri");
            List<String> allergen = (List<String>) familyInfo.get("allergen");
            List<Integer> label = (List<Integer>) familyInfo.get("label");
            List<Integer> taste = (List<Integer>) familyInfo.get("taste");
            for(Double item : nutri) {
                argstemp.add(item.toString());
            }
            argstemp.add(String.valueOf(allergen.size()));
            argstemp.addAll(allergen);
            argstemp.add(String.valueOf(label.size()));
            for(Integer item : label) {
                argstemp.add(item.toString());
            }
            argstemp.add(String.valueOf(taste.size()));
            for(Integer item : taste) {
                argstemp.add(item.toString());
            }
        }
        String[] args1 = argstemp.toArray(new String[0]);
        for(String args : args1) {
            System.out.print(args+' ');
        }
        System.out.println();
        Process proc;
        proc = Runtime.getRuntime().exec(args1);
        BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), StandardCharsets.UTF_8));
        String line;
        List<String> res = new ArrayList<>();
        while ((line = in.readLine()) != null) {
            res.add(line);
        }
        System.out.println(res);
        in.close();
        //waitFor是用来显示脚本是否运行成功，1表示失败，0表示成功，还有其他的表示其他错误
        int re = proc.waitFor();
        System.out.println(re);
        List<Recipe> recipes = new ArrayList<>();
        List<Double> scores = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        for(int i = 0; i < 3; i++) {
            String rec = res.get(i);
            List<String> recipelist = Arrays.asList(rec.split(" "));
            for(String t : recipelist) {
                recipes.add(recipeService.findRecipebyName(t));
            }
        }
        for(int i = 3; i < 24; i++) scores.add(Double.valueOf(res.get(i)));
        data.put("score",scores);
        data.put("recipe",recipes);
        return new ResponseResult(200,"返回成功", data);
    }

    @PostMapping("/change/setting")
    public ResponseResult ChangeSetting(@CurrentUserId String userId,@RequestBody Float[] settings){
        recordService.UpdateSettings(userId,settings);
        return new ResponseResult(200,"修改成功");
    }

    @PostMapping("/get_settings")
    public ResponseResult GetSetting(@CurrentUserId String userId){
        Settings settings = recordService.getSettings(userId);
        List<String> allergen = userService.findAllergenById(userId);
        List<String> disease = userService.findDiseaseById(userId);
        Map<String,Object> temp = new HashMap<>();
        temp.put("settings",settings);
        temp.put("allergens",allergen);
        temp.put("diseases",disease);
        return new ResponseResult(200,temp);
    }

}
