/*
API的请求方法
 */
package cn.chmlfrp;
import java.io.IOException;

import com.alibaba.fastjson.JSON;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.alibaba.fastjson.JSONObject;

public class ChmlFrpApiGet {
    public ChmlFrpUserInfo UserInfo = new ChmlFrpUserInfo();
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
    public String ApiLoginP(String username,String password) throws IOException {
        String content = "username=" + username + "&password=" + password;
        JSONObject ApiReturn = SendGet(ChmlFrpApi.login,content);
        if (ApiReturn.containsKey("error")) {
            return ApiReturn.getString("error");
        }
        UserInfo.setUserid(ApiReturn.getString("userid"));
        UserInfo.setUsername(ApiReturn.getString("username"));
        UserInfo.setEmail(ApiReturn.getString("email"));
        UserInfo.setQq(ApiReturn.getString("qq"));
        UserInfo.setUsergroup(ApiReturn.getString("usergroup"));
        UserInfo.setBandwidth(ApiReturn.getString("bandwidth"));
        UserInfo.setTunnel(ApiReturn.getString("tunnel"));
        UserInfo.setToken(ApiReturn.getString("token"));
        UserInfo.setRealname(ApiReturn.getString("realname"));
        UserInfo.setTerm(ApiReturn.getString("term"));
        AllMagic.AllUserInfo = UserInfo;
        return "你好, " + username;
    }
    public String ApiCreateTunnel(String token,String userid,String localip,String name,String node,String type,String nport,String dorp,String ap,String encryption,String compression) throws IOException {
        JSONObject ApiJsonIn = null;
        ApiJsonIn = new JSONObject();
        ApiJsonIn.put("token", token);
        ApiJsonIn.put("userid", userid);
        ApiJsonIn.put("localip", localip);
        ApiJsonIn.put("name", name);
        ApiJsonIn.put("node", node);
        ApiJsonIn.put("type", type);
        ApiJsonIn.put("nport", nport);
        ApiJsonIn.put("dorp", dorp);
        ApiJsonIn.put("ap", ap);
        ApiJsonIn.put("encryption", encryption);
        ApiJsonIn.put("compression", compression);
        JSONObject ApiReturn = SendGet(ChmlFrpApi.createtunnel,ApiJsonIn.toJSONString());
        return ApiReturn.toString();
    }
    public String ApiCreateTunnelJson(JSONObject ApiJsonIn) throws IOException {
        JSONObject ApiReturn = SendGet(ChmlFrpApi.createtunnel,ApiJsonIn.toJSONString());
        return ApiReturn.toString();
    }
}

