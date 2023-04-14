package com.example.diet.Controller;

import com.example.diet.Domain.FamilyInfo;
import com.example.diet.Domain.Recipe;
import com.example.diet.Domain.ResponseResult;
import com.example.diet.Resolver.CurrentUserId;
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
    private UserController userController;

    @Autowired
    private RecipeController recipeController;

    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> argstemp = new ArrayList<String>();
        argstemp.add("C:\\Users\\KK\\.conda\\envs\\KK\\python.exe");
//        argstemp.add("/root/anaconda3/envs/KK/bin/python")
        argstemp.add("src/main/resources/static/recommend.py");
        String[] ingredients = new String[]{"牛肉", "番茄", "土豆", "猪肉", "鸡肉", "西兰花", "辣椒", "黄瓜"};
        List<Integer> user1 = new ArrayList<Integer>(){{add(10);add(200);add(30);}};
        List<Integer> user2 = new ArrayList<Integer>(){{add(10);add(300);add(30);}};
        List<Integer> user3 = new ArrayList<Integer>(){{add(5);add(100);add(10);}};
        List<List<Integer> > users = new ArrayList<List<Integer>>(){{add(user1);add(user2);add(user3);}};
        List<String> allergen1 = new ArrayList<String>(){{add("土豆");}};
        List<String> allergen2 = new ArrayList<String>(){{add("螃蟹");}};
        List<String> allergen3 = new ArrayList<String>(){{add("包菜");}};
        List<List<String>> allergen = new ArrayList<List<String>>(){{add(allergen1);add(allergen2);add(allergen3);}};
        argstemp.add("3");
        argstemp.add(String.valueOf(ingredients.length));
        Collections.addAll(argstemp, ingredients);
        for (List<Integer> user : users) {
            for (Integer integer : user) {
                argstemp.add(integer.toString());
            }
        }
        for (List<String> strings : allergen) {
            argstemp.add(String.valueOf(strings.size()));
            argstemp.addAll(strings);
        }
        String[] args1 = argstemp.toArray(new String[0]);
        Process proc;
        proc = Runtime.getRuntime().exec(args1);
        BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"GBK"));
        String line;
        List<String> res = new ArrayList<>();
        while ((line = in.readLine()) != null) {
            res.add(line);
        }
        List<List<String> > comblist = new ArrayList<>();
        for (String s : res) {
            comblist.add(Arrays.asList(s.split("")));
        }
        System.out.println(comblist);
        in.close();
        //waitFor是用来显示脚本是否运行成功，1表示失败，0表示成功，还有其他的表示其他错误
        int re = proc.waitFor();
        System.out.println(re);
    }

    @PostMapping("/recipe")
    public ResponseResult recommend(@RequestBody String[] recipe, @CurrentUserId String userid) throws IOException, InterruptedException {
        List<String> argstemp = new ArrayList<>();
//        argstemp.add("C:\\Users\\KK\\.conda\\envs\\KK\\python.exe");
        argstemp.add("/root/anaconda3/envs/KK/bin/python");
//        argstemp.add("src/main/resources/static/recommend.py");
        argstemp.add("/root/recommend/recommend.py");
        for (String s : recipe) {
            System.out.println(s);
        }
        List<FamilyInfo> familyInfos = userController.findFamilyByusrId(userid);
        for(FamilyInfo familyInfo : familyInfos) {
            System.out.println(familyInfo);
        }
        List<List<String>> allergen = new ArrayList<>();
        argstemp.add(String.valueOf(familyInfos.size()));
        argstemp.add(String.valueOf(recipe.length));
        Collections.addAll(argstemp, recipe);
        for (FamilyInfo familyInfo : familyInfos) {
            argstemp.add(familyInfo.getSugar_need().toString());
            argstemp.add(familyInfo.getCal_need().toString());
            argstemp.add(familyInfo.getFat_need().toString());
            allergen.add(Arrays.asList(familyInfo.getAllergen().split("、")));
        }
        for (List<String> strings : allergen) {
            argstemp.add(String.valueOf(strings.size()));
            argstemp.addAll(strings);
        }
        String[] args1 = argstemp.toArray(new String[0]);
        for(String args : args1) {
            System.out.print(args+' ');
        }
        Process proc;
        proc = Runtime.getRuntime().exec(args1);
        BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), StandardCharsets.UTF_8));
        String line;
        List<String> res = new ArrayList<>();
        while ((line = in.readLine()) != null) {
            res.add(line);
        }
        List<String> comblist = new ArrayList<>();
        List<String> comblist2 = new ArrayList<>();
        comblist2.add("番茄蛋花汤");
        comblist2.add("胡萝卜红烧肉");
        comblist2.add("醋溜白菜");
        for (String s : res) {
            comblist.addAll(Arrays.asList(s.split(" ")));
        }
        System.out.println(comblist);
        in.close();
        //waitFor是用来显示脚本是否运行成功，1表示失败，0表示成功，还有其他的表示其他错误
        int re = proc.waitFor();
        System.out.println(re);
        List<Recipe> data = new ArrayList<>();
        for (String s : comblist) {
            System.out.println(s);
            data.add(recipeController.findRecipebyName(s));
        }
        System.out.println("Recommended complete successfully!");
        return new ResponseResult(200,"返回成功", data);
    }

}
