/*
测试已经完成的方法是否达到预期效果
 */
package cn.chmlfrp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class test {
    public static void main(String[] args) throws Exception {
        ChmlFrpApiGet ApiGet = new ChmlFrpApiGet();
        System.out.println(ApiGet.ApiLoginP("123456","123456"));
    }
}
