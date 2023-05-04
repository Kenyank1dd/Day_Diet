package com.example.diet.Controller;

import com.example.diet.Domain.ResponseResult;
import com.example.diet.Util.ChatGPTapiUtil;
import com.example.diet.Util.ImageUtil;
import com.example.diet.Util.TranslateUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/generate")
public class GenerateController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/plating")
    public ResponseResult PictureGenerate(@RequestParam("rec_name") String recname) throws IOException {
        /**
         * getForObject
         * 参数1 要请求的地址的url  必填项
         * 参数2 响应数据的类型 是String 还是 Map等 必填项
         * 参数3 请求携带参数 选填
         * getForObject 方法的返回值就是 被调用接口响应的数据
         */
        switch (recname) {
            case "红烧肉":
                recname = "a photo of hongshaorou";
                break;
            case "番茄牛腩":
                recname = "a photo of fanqieniunan";
                break;
            case "番茄炒鸡蛋":
                recname = "a photo of fanqiechaojidan";
                break;
            case "土豆丝":
                recname = "a photo of tudousi";
                break;
            case "清蒸鲈鱼":
                recname = "a photo of qingzhengluyu";
                break;
            case "西瓜牛肉炒土豆":
                recname = "(potato:1.0), (watermelon:1.0), (beef:1.0), (ginger:0.5), (garlic:0.3)," +
                        " (salt:0.5), (cooking wine:0.3), (soy sauce:0.5), (vegetable oil:0.5), " +
                        "shredded, stir-fried, (savory and sweet:0.8), (tender and juicy:0.8), (aromatic:0.5)";
                break;
            default:
                recname = TranslateUtil.translate(recname) + ",Chinese cuisine with bright colors";
        }
        Gson gson = new Gson();
        String url = "http://region-3.seetacloud.com:16682/sdapi/v1/txt2img";
        Map<String,String> paramMap = new HashMap<String, String>();

        paramMap.put("prompt", recname);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .build();
        String jsonString = gson.toJson(paramMap);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();
        Call call = client.newCall(request);

        try (Response response = call.execute()) {
            String result = response.body().string();
            JsonObject obj = gson.fromJson(result, JsonObject.class);
            result = obj.get("images").getAsString();
            String imageurl = ImageUtil.generateImage(result,"/root/sd/");
            System.out.println(imageurl);
            return new ResponseResult(200,"生成成功！", imageurl);
        } catch (IOException e) {
            throw new IOException(e);
        }


//        paramMap.put("prompt", "Chinese Cuisine, Shredded Cucumber, with Exquisite plating, High color saturation, Realistic style, Overhead view");
        //先获取返回的字符串，若想获取属性，可以使用gson转化为实体后get方法获取
//        String result = restTemplate.postForObject(url, paramMap, String.class);
//        Gson gson = new Gson();
//        JsonObject obj = gson.fromJson(result, JsonObject.class);
//        result = obj.get("images").getAsString();
//
//        return util.ImageUtil.generateImage(result,"D:\\Desktop\\");
    }

    @PostMapping("/recipe")
    public ResponseResult RecipeGenerate(@org.springframework.web.bind.annotation.RequestBody String[] ing_list) {
        StringBuilder query = new StringBuilder("我现在有以下食材：");
        for (String s : ing_list) {
            query.append(s).append("、");
        }
        query.append("\n请用这些食材为我生成一个非常创新、少见的菜谱");
        return new ResponseResult(200,ChatGPTapiUtil.chat(query));
    }
}
