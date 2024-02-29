package cn.chmlfrp;
import com.alibaba.fastjson.JSONObject;
import com.mojang.brigadier.*;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;
import org.apache.logging.log4j.core.jmx.Server;

import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;
import static net.minecraft.server.command.CommandManager.literal;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;

import java.io.IOException;

public class ChmlFrpCommend {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> chmlfrp = literal("chmlfrp")
                .executes(c -> sendCommendMessage(c.getSource().getPlayer(), "§l§b<ChmlFrpINFO> §fChmlFrp的指令为\"/clp\""));

        dispatcher.register(chmlfrp);
        LiteralArgumentBuilder<ServerCommandSource> clp =
                literal("clp")
                        .requires(source -> source.hasPermissionLevel(4))
                        .executes(c -> {
                            sendCommendMessage(c.getSource().getPlayer(), "§l§b<ChmlFrpINFO> §f输入\"/clp help\"获取帮助列表");
                            return 1;
                        });
        clp.then(literal("help")
                .requires(source -> source.hasPermissionLevel(4))
                .executes(c -> {
                    sendCommendMessage(c.getSource().getPlayer(), "§l§2#########§l§aChmlFrpFabric帮助列表§l§2#########" + "\n" +
                            "§f/clp help  §e#获取帮助列表" + "\n" +
                            "§f/clp login [token]  §e#登录ChmlFrp" + "\n" +
                            "§f/clp user  §e#查看用户信息" + "\n" +
                            "§f/clp list  §e#查看隧道列表" + "\n" +
                            "§f/clp run [隧道ID]  §e#启动指定隧道" + "\n" +
                            "§f/clp stop [隧道ID]  §e#关闭指定隧道" + "\n" +
                            "§f/clp logout  §e#查看Frp输出" + "\n" +
                            "§f/clp autorun [隧道ID]  §e#MC运行时自动启动隧道" + "\n" +
                            "§f/clp version  §e#查看模组版本" + "\n");
                    return 1;
                }));
        clp.then(literal("login")
                .requires(source2 -> source2.hasPermissionLevel(4))
                .executes(c -> {
                    sendCommendMessage(c.getSource().getPlayer(), "§l§b<ChmlFrpINFO> §f请输入用户名以及密码");
                    return 1;
                }).then(argument("token", StringArgumentType.string())).executes(c2 -> {
                    ChmlFrpApiGet chmlFrpApiGet = new ChmlFrpApiGet();
                    JSONObject ApiReturn;
                    try {
                        ApiReturn = chmlFrpApiGet.SendGet(ChmlFrpApi.userinfo,StringArgumentType.getString(c2, "token"));
                        sendCommendMessage(c2.getSource().getPlayer(),"正在使用密钥为" + String.valueOf(StringArgumentType.getString(c2,"token")) + "进行登录");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if(ApiReturn.getString("code").equals("200")) {
                        sendCommendMessage(c2.getSource().getPlayer(), "§l§b<ChmlFrpINFO> §f登录成功");
                    }else {
                        sendCommendMessage(c2.getSource().getPlayer(), "§l§b<ChmlFrpINFO> §f登录失败");
                    }
                    System.out.println(ApiReturn.toJSONString());
                    sendCommendMessage(c2.getSource().getPlayer(), "§l§b<ChmlFrpINFO> §fChmlFrp隧道信息");
                    return 0;
                }));
        clp.then(literal("user")
                .requires(source -> source.hasPermissionLevel(4))
                .executes(c -> {
                    sendCommendMessage(c.getSource().getPlayer(), "§l§b<ChmlFrpINFO> §fChmlFrp用户信息");
                    return 1;
                }));

        dispatcher.register(clp);
    }

    private static int sendCommendMessage(PlayerEntity player, String Content) {
        player.sendMessage(Text.literal(Content));
        return Command.SINGLE_SUCCESS;
    }
}

 /*
public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
    LiteralArgumentBuilder<ServerCommandSource> chmlfrp = literal("chmlfrp")
            .requires(source -> source.hasPermissionLevel(4));

    LiteralArgumentBuilder<ServerCommandSource> clp =
            literal("clp")
                    .then(literal("login")
                            .executes(c -> {
                                sendCommendMessage(c.getSource().getPlayer(), "请输入用户名和密码");
                                return 1;
                            })
                            .then(argument("username", StringArgumentType.word())
                                    .then(argument("password", StringArgumentType.greedyString())
                                            .executes(c -> {
                                                String username = StringArgumentType.getString(c, "username");
                                                String password = StringArgumentType.getString(c, "password");

                                                // 在这里处理登录逻辑，例如验证用户名和密码
                                                // 这里仅用于演示，将接收到的用户名和密码打印出来
                                                sendCommendMessage(c.getSource().getPlayer(), "接收到的用户名：" + username + "，密码已接收但不显示");
                                                return 1;
                                            })));

    chmlfrp.then(clp);
    dispatcher.register(chmlfrp);
}

private static int sendCommendMessage(ServerCommandSource source, String content) {
    source.sendFeedback(new LiteralText(content), false);
    return Command.SINGLE_SUCCESS;
}
*/