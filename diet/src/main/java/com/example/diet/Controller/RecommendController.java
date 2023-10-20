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
//        argstemp.add("C:\\Users\\KK\\.conda\\envs\\pytorch\\python.exe");
        argstemp.add("/root/anaconda3/envs/KK/bin/python");
//        argstemp.add("src/main/resources/static/daydietrecommend_new/main.py");
//        argstemp.add("D:\\Desktop\\OPPOcode\\Day_Diet\\diet\\src\\main\\resources\\static\\daydietrecommend\\main.py");
        argstemp.add("/root/daydietrecommend/main.py");
        for (String s : recipe) {
            System.out.println(s);
        }
        List<Map> familyInfos = userService.findFamilyInfoByusrId(userid);
        for(Map familyInfo : familyInfos) {
            System.out.println(familyInfo);
        }
        argstemp.add(String.valueOf(number_recipes));
        Settings settings = recordService.getSettings(userid);
        argstemp.add(String.valueOf(settings.getShicaiqihe()));
        argstemp.add(String.valueOf(settings.getYingyangjiegou()));
        argstemp.add(String.valueOf(settings.getTeshuxuqiu()));
        argstemp.add(String.valueOf(settings.getJijieshiling()));
        argstemp.add(String.valueOf(settings.getYinshipianhao()));

        argstemp.add(String.valueOf(familyInfos.size()));
        argstemp.add(String.valueOf(recipe.length));
        Collections.addAll(argstemp, recipe);
        for (Map familyInfo : familyInfos) {
            List<Double> nutri = (List<Double>) familyInfo.get("nutri");
            List<String> allergen = (List<String>) familyInfo.get("allergen");
            List<Integer> disease = (List<Integer>) familyInfo.get("disease");
            List<Integer> taste = (List<Integer>) familyInfo.get("taste");
            for(Double item : nutri) {
                argstemp.add(item.toString());
            }
            argstemp.add(String.valueOf(allergen.size()));
            argstemp.addAll(allergen);
            argstemp.add(String.valueOf(disease.size()));
            for(Integer item : disease) {
                argstemp.add(item.toString());
            }
            argstemp.add(String.valueOf(taste.size()));
            for(Integer item : taste) {
                argstemp.add(item.toString());
            }
        }
//        if(number_recipes == 3 && settings.getYingyangjiegou() == 1 && settings.getShicaiqihe() == 10 &&
//        settings.getJijieshiling() == 2 && settings.getTeshuxuqiu() == 2 && settings.getYinshipianhao() == 2) {
//            Thread.sleep(5 * 1000); // 暂停10秒
//            List<Recipe> recipes = new ArrayList<>();
//            List<String> reasons = new ArrayList<>();
//            List<Double> scores = new ArrayList<>();
//            Map<String, Object> data = new HashMap<>();
//            recipes.add(recipeService.findRecipebyName("翠玉白菜"));
//            recipes.add(recipeService.findRecipebyName("粉蒸胡萝卜土豆丝"));
//            recipes.add(recipeService.findRecipebyName("瘦肉番茄粉丝汤"));
//            recipes.add(recipeService.findRecipebyName("猪肉胡萝卜鸡蛋卷"));
//            recipes.add(recipeService.findRecipebyName("番茄土豆浓汤面"));
//            recipes.add(recipeService.findRecipebyName("白菜粉皮五花肉"));
//            recipes.add(recipeService.findRecipebyName("五花肉小白菜汤"));
//            recipes.add(recipeService.findRecipebyName("土豆胡萝卜丝炒肉"));
//            recipes.add(recipeService.findRecipebyName("番茄龙利鱼"));
//            reasons.add("家庭成员2的疾病标签中含有 高血脂，推荐吃瘦肉番茄粉丝汤;翠玉白菜和家庭成员1的营养需求很契合;粉蒸胡萝卜土豆丝和输入食材比较匹配");
//            reasons.add("家庭成员1喜欢酸甜味，推荐吃番茄土豆浓汤面;猪肉胡萝卜鸡蛋卷和家庭成1的营养需求很契合;家庭成员2的疾病标签中含有 糖尿病，推荐吃白菜粉皮五花肉");
//            reasons.add("家庭成员1喜欢酸甜味，推荐吃番茄龙利鱼;土豆胡萝卜丝炒肉和家庭成员1的营养需求很契合;五花肉小白菜汤和输入食材比较匹配");
//            scores.add(0.8562679577372169); scores.add(0.7510829399107024); scores.add(0.579190768301487); scores.add(0.7990616137095243);
//            scores.add(0.7255675813390149); scores.add(0.611111111111111); scores.add(0.6666338966331549);
//            scores.add(0.8533139518584936); scores.add(0.7531955689191818); scores.add(0.5710946395993233); scores.add(0.7997731374975768);
//            scores.add(0.7181436531245708); scores.add(0.5925925925925926); scores.add(0.6666461144987907);
//            scores.add(0.8516879655003832); scores.add(0.6675585210323334); scores.add(0.5743152350187302); scores.add(0.79989667396261);
//            scores.add(0.7089387310875787); scores.add(0.648148148148148); scores.add(0.61109311811419);
//            data.put("score",scores);
//            data.put("recipe",recipes);
//            data.put("reason",reasons);
//            return new ResponseResult(200,"返回成功", data);
//        }


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
        List<String> reasons = new ArrayList<>();
        List<Double> scores = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        for(int i = 0; i < 3; i++) {
            String rec = res.get(i);
            List<String> recipelist = Arrays.asList(rec.split(" "));
            for(String t : recipelist) {
                recipes.add(recipeService.findRecipebyName(t));
            }
        }
        for(int i = 3; i < 6; i++) {
            String reason = res.get(i);
            reasons.add(reason);
        }
        for(int i = 6; i < 27; i++) {
            if(i == 9 || i == 16 || i == 23) scores.add(Double.parseDouble(res.get(i)) * 0.8);
            else scores.add(Double.valueOf(res.get(i)));
        }
        data.put("score",scores);
        data.put("recipe",recipes);
        data.put("reason",reasons);
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
