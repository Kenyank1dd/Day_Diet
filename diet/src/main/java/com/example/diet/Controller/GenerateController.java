package com.example.diet.Controller;

import com.example.diet.Domain.ResponseResult;
import com.example.diet.Util.ChatGPTapiUtil;
import com.example.diet.Util.ImageUtil;
import com.example.diet.Util.RecipeParseUtil;
import com.example.diet.Util.TranslateUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/generate")
public class GenerateController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/plating")
    public ResponseResult PictureGenerate(@RequestParam("rec_name") String recname, @RequestParam("index") Integer index) throws Exception {
        /**
         * getForObject
         * 参数1 要请求的地址的url  必填项
         * 参数2 响应数据的类型 是String 还是 Map等 必填项
         * 参数3 请求携带参数 选填
         * getForObject 方法的返回值就是 被调用接口响应的数据
         */
        System.out.println(index);
        recname ="RAW photo,masterpiece, high quality, best quality," + "(" + TranslateUtil.translate(recname) + ")" + ", <lora:foodphoto:0.65>, (top view),(close up)";
        Gson gson = new Gson();
        String url = "http://region-45.seetacloud.com:53819/sdapi/v1/img2img";
        if(index == 0) url = "http://region-45.seetacloud.com:53819/sdapi/v1/txt2img";
        Map<String,Object> paramMap = new HashMap<String, Object>();

        paramMap.put("prompt", recname);
        paramMap.put("negative_prompt", "out of focus, cropped, ((background_noise)), (worst quality, low quality:1.3), (blurring:1.2), blinding light, (((symmetry, symmetrical pose))), folded torso, two heads, two faces, two torsos, totem, two people, badly drawn face, extra limb, ugly, worse quality, low quality, jpeg artifacts, mutation, mutated, hideous, badly drawn arms, missing limb, floating limb, severed limb, badly drawn face, mutation, deformed, blurry, dehydrated, bad anatomy, bad proportions, extra limbs, cloned face, mutilated, rough proportions, blurry, watermark, oversaturated, censorship, distorted hands, amputation, missing hands, double face, double hands, round windows, circle, cloudy eyes, double body, lifeless expression");
        if(index != 0) paramMap.put("denoising_strength",0.6);

        List<String> base64 = new ArrayList<>();
        if(index != 0) {
            String path = "/root/PlatingTemplate/" + index + ".png";
            base64.add(ImageUtil.getImageBase(path));
            paramMap.put("init_images", base64);
        }
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
    public ResponseResult RecipeGenerate(@org.springframework.web.bind.annotation.RequestBody String[] ing_list, @RequestParam(value = "gongyi") String gongyi, @RequestParam(value = "nandu") String nandu, @RequestParam(value = "weidao") String weidao) throws IOException {
        String url = "https://u22566-a63c-a4d8108a.beijinga.seetacloud.com/";
        StringBuilder query = new StringBuilder("以");
        for (String s : ing_list) {
            query.append(s).append("、");
        }
        query.append("为主要食材，生成一道菜谱，要求：难度为");
        query.append(nandu);
        query.append("，口味为");
        query.append(weidao);
        query.append("，工艺为");
        query.append(gongyi);
        query.append("。你的回答需要包含菜名、食材、制作步骤以及小贴士。");

        Gson gson = new Gson();
        Map<String,String> paramMap = new HashMap<String, String>();

        paramMap.put("prompt", String.valueOf(query));

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

        String result;
        try (Response response = call.execute()) {
            result = response.body().string();
            System.out.println(result);
            JsonObject obj = gson.fromJson(result, JsonObject.class);
            result = obj.get("response").getAsString();
        } catch (IOException e) {
            throw new IOException(e);
        }

//        String res = ChatGPTapiUtil.chat(query);
        Map<String,Object> data = RecipeParseUtil.RecipeParse(result);
        return new ResponseResult(200,data);
    }
}