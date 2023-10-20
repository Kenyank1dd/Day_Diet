package com.example.daydiet.service;

import android.util.Log;

import com.example.daydiet.model.entity.ChatRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatUtil {

    public String chat(String input, List<List<String>> history) throws IOException {

        String url = "http://region-3.seetacloud.com:12276/";
//        String url = "https://u22566-8832-dcf73636.beijinga.seetacloud.com/";
        StringBuilder query = new StringBuilder(input);

         // 将 List<List<String>> 转换为 String[][]
        String[][] historyArray = new String[history.size()][];
        for (int i = 0; i < history.size(); i++) {
            List<String> innerList = history.get(i);
            historyArray[i] = innerList.toArray(new String[innerList.size()]);
        }
        Log.d("++++666",historyArray.toString());

        Gson gson = new Gson();
        ChatRequest chatRequest = new ChatRequest(input,historyArray);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .build();
        String jsonString = gson.toJson(chatRequest);
        okhttp3.RequestBody body = okhttp3.RequestBody.create(MediaType.parse("application/json"), jsonString);
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();
        Call call = client.newCall(request);
        String result;
        try (Response response = call.execute()) {
            result = response.body().string();
            System.out.println(result);
            Log.d("++++dddd", result);

            JsonObject obj = gson.fromJson(result, JsonObject.class);
            result = obj.get("response").getAsString();
        } catch (IOException e) {
            throw new IOException(e);
        }

        return result;
    }
}
