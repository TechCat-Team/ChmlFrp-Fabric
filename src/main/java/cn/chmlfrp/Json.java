package cn.chmlfrp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

public class Json {
    public static void main(String[] args) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject = JSON.parseObject("{\n" +
                "    \"token\": \"nRZNyFPtzZhrxjTFproUdrmI\",\n" +
                "    \"userid\": \"29\",\n" +
                "    \"localip\": \"127.0.0.1\",\n" +
                "    \"name\": \"RTCraft-Java\", //隧道昵称\n" +
                "    \"node\": \"北京移动\", //节点名\n" +
                "    \"type\": \"tcp\", //类型 可以为：tcp、udp、http、https\n" +
                "    \"nport\": 25574, //内网端口\n" +
                "    \"dorp\": 25565, // 外网端口\n" +
                "    \"ap\": \"\", //额外参数\n" +
                "    \"encryption\": \"false\", //数据加密\n" +
                "    \"compression\": \"false\" //数据压缩\n" +
                "}");
        ChmlFrpApiGet ChmlFrpApiGet = new ChmlFrpApiGet();
        System.out.println(ChmlFrpApiGet.ApiCreateTunnel("8qBKPweo1ZUiGLBhCQiKVVwY","29","127.0.0.1","RTCraft-Java","北京移动","tcp","25574","25565","","false","false"));
        System.out.println(ChmlFrpApiGet.ApiCreateTunnelJson(jsonObject));

    }
}
