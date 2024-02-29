package cn.chmlfrp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class test {
    public static void main(String[] args) throws Exception {
        ChmlFrpApiGet chmlFrpApiGet = new ChmlFrpApiGet();
        JSONObject ApiReturn = chmlFrpApiGet.SendGet(ChmlFrpApi.login,"username=chaoji233&password=MC_CHAOJI233");
        System.out.println(ApiReturn.toJSONString());
    }
}
