package com.example.diet.Util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;
import top.jfunc.json.impl.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TranslateUtil {
    public static final String API_KEY = "mN3sYT1cAneeZUuHw9dBSdbv";
    public static final String SECRET_KEY = "wxFGzslxonjT8oKSqBr6UrdFR7SiviNe";

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public static String translate(String rec_name) throws IOException {
        Gson gson = new Gson();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"from\":\"zh\",\"to\":\"en\",\"q\":\""+ rec_name + "\"}");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/mt/texttrans/v1?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        JsonObject jsonObject = gson.fromJson(Objects.requireNonNull(response.body()).string(), JsonObject.class);
        String dst = jsonObject.getAsJsonObject("result")
                .getAsJsonArray("trans_result").get(0)
                .getAsJsonObject().get("dst").getAsString();
        return dst;
    }

    static String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }
}
