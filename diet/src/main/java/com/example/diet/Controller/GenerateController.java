package com.example.diet.Controller;

import com.example.diet.Util.ChatGPTapiUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
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
    public String PictureGenerate(@RequestParam("rec_name") String recname) throws IOException {
        /**
         * getForObject
         * 参数1 要请求的地址的url  必填项
         * 参数2 响应数据的类型 是String 还是 Map等 必填项
         * 参数3 请求携带参数 选填
         * getForObject 方法的返回值就是 被调用接口响应的数据
         */
        Gson gson = new Gson();
        String url = "http://region-3.seetacloud.com:16682/sdapi/v1/txt2img";
        Map<String,String> paramMap = new HashMap<String, String>();
        recname = "a photo of " + recname;
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
            System.out.println(result);
            return util.ImageUtil.generateImage(result,"D:\\Desktop\\");
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

    @RequestMapping("/recipe")
    public String RecipeGenerate(@org.springframework.web.bind.annotation.RequestBody String[] ing_list) {
        StringBuilder query = new StringBuilder("我现在有以下食材：");
        for (String s : ing_list) {
            query.append(s).append("、");
        }
        query.append("\n请用这些食材为我生成一个非常创新、少见的菜谱");
        return ChatGPTapiUtil.chat(query);
    }
}
