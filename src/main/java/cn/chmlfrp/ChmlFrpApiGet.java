package cn.chmlfrp;
import java.io.IOException;

import com.alibaba.fastjson.JSON;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.alibaba.fastjson.JSONObject;

public class ChmlFrpApiGet {
    //private final static String API_URL = "https://panel.chmlfrp.cn/api/login.php";

    private OkHttpClient client;

    public ChmlFrpApiGet() {
        this.client = new OkHttpClient();
    }

    public JSONObject SendGet(String httpapi,String content) throws IOException {
        String API_URL = httpapi + "?" +content;
        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            JSONObject ApiReturn = JSON.parseObject(responseBody);
            //String ApiReturnErrorMessage = ApiReturn.getString("error");
            return (ApiReturn);
        }
    }
}

