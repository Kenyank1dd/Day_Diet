package com.example.diet.Util;

import okhttp3.*;
import top.jfunc.json.impl.JSONObject;

import java.io.IOException;

public class TokenUtil {
    public static String getAccessToken(String API_KEY, String SECRET_KEY) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }
}
