package com.example.diet.Util;

import cn.hutool.core.convert.ConvertException;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class ChatGPTapiUtil {
    /**
     * 聊天端点
     */
    String chatEndpoint = "https://open2.aiproxy.xyz/v1/chat/completions";
    /**
     * api密钥
     */
    String apiKey = "Bearer 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";

    /**
     * 发送消息
     *
     * @param txt 内容
     * @return {@link String}
     */
    public String chat(String txt) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", "gpt-3.5-turbo");
        List<Map<String, String>> dataList = new ArrayList<>();
        dataList.add(new HashMap<String, String>(){{
            put("role", "user");
            put("content", txt);
        }});
        paramMap.put("messages", dataList);
//        paramMap.put("stream", String.valueOf(true));
        JSONObject message = null;
        try {
            String body = HttpRequest.post(chatEndpoint)
                    .header("Authorization", apiKey)
                    .header("Content-Type", "application/json")
//                    .header("Content-Type", "text/event-stream")
//                    .header("Transfer-Encoding","chunked")
                    .body(JSONUtil.toJsonStr(paramMap))
                    .timeout(200000)
                    .execute()
                    .body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            JSONArray choices = jsonObject.getJSONArray("choices");
            JSONObject result = choices.get(0, JSONObject.class, Boolean.TRUE);
            message = result.getJSONObject("message");
        } catch (HttpException | ConvertException e) {
            return "出现了异常";
        }
        return message.getStr("content");
    }



    public static void main(String[] args) {
        System.out.println(chat(""));
    }
}
