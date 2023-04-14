package com.example.diet.Controller;

import com.example.diet.Domain.ResponseResult;
import com.example.diet.Util.ChatGPTapiUtil;
import com.example.diet.Util.ImageUtil;
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
        Gson gson = new Gson();
        String url = "http://region-3.seetacloud.com:16682/sdapi/v1/txt2img";
        recname = "fanqieniunan";
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
            String imageurl = ImageUtil.generateImage(result,"/root/sd/");
            System.out.println(imageurl);
            return new ResponseResult(200,"生成成功！", "/root/sd/111.png");
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
        ChatGPTapiUtil.chat(new StringBuilder("和我说五遍你好"));
        return new ResponseResult(200,"菜品名称：西瓜牛肉炒土豆\n" +
                "\n" +
                "食材清单：\n" +
                "\n" +
                "土豆\n" +
                "西瓜\n" +
                "牛肉\n" +
                "姜片\n" +
                "蒜瓣\n" +
                "盐\n" +
                "料酒\n" +
                "生抽\n" +
                "熟菜油\n" +
                "制作步骤：\n" +
                "\n" +
                "将土豆去皮，用水冲洗干净，沥干备用。\n" +
                "西瓜去皮、去籽，切成小块；牛肉切成薄片，加入少量料酒和生抽腌制一下备用。\n" +
                "热锅冷油，加入姜片和蒜瓣，爆香后捞出不要。\n" +
                "加入适量的熟菜油，放入土豆，中火煸炒至土豆变软，加入盐调味。\n" +
                "加入腌好的牛肉片，翻炒几下，加入西瓜块，快速翻炒几下，不要煮太久，以保持西瓜的鲜甜口感。\n" +
                "加入之前爆香的姜片和蒜瓣，快速翻炒均匀后即可出锅。");
    }
}
