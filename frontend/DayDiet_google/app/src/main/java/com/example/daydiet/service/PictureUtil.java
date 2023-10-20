package com.example.daydiet.service;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PictureUtil {
    public static final String API_KEY = "9Olsvwarb7N2c244RMNU50bn";
    public static final String SECRET_KEY = "6Gf7Lj4kMOKsetQjmvr6Pv0rWqBAG7lG";
    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public JSONObject identify(String filePath) throws IOException, JSONException {

        // 读取图片文件的内容
        File file2 = new File(filePath);
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(file2);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("++++6622", "##### file code: " + data.length);
        String imageBase64 = Base64.encodeToString(data, Base64.NO_CLOSE);


        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String picbase = imageBase64;
        System.out.println(picbase);
        String imgParam = URLEncoder.encode(picbase, "UTF-8");
        Log.d("++++6622", "##### file code:" + imgParam.length());
//        Log.d("++++6622", "##### file code:" + imgParam);

        RequestBody body = RequestBody.create(mediaType, "image=" + imgParam + "&top_num=" + 5 + "&filter_threshold=" + 0.85 + "&baike_num=" + 2);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/image-classify/v2/dish?access_token=" + TokenUtil.getAccessToken(API_KEY,SECRET_KEY))
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();

        JSONObject jsonObject = new JSONObject(response.body().string());
        Log.d("++++6622", jsonObject.toString());
        JSONArray resultArray = jsonObject.getJSONArray("result");
        String firstFood = resultArray.getJSONObject(0).get("name").toString();
        return jsonObject;
    }

}
