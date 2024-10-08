package cn.chmlfrp;
import com.alibaba.fastjson.JSONObject;
import com.mojang.brigadier.*;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.sun.jna.platform.win32.Netapi32Util;
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
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;


import java.io.IOException;

public class ChmlFrpCommend {
    public ChmlFrpUserInfo UserInfo = new ChmlFrpUserInfo();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> chmlfrp = literal("chmlfrp")
                .executes(c -> sendCommendMessage(c.getSource().getPlayer(), "§l§b<ChmlFrpINFO> §fChmlFrp的指令为\"/cf\""));

        dispatcher.register(chmlfrp);
        LiteralArgumentBuilder<ServerCommandSource> cf = literal("cf")
                        .requires(source -> source.hasPermissionLevel(4))
                        .executes(c -> {
                            sendCommendMessage(c.getSource().getPlayer(), "§l§b<ChmlFrpINFO> §f输入\"/cf help\"获取帮助列表");
                            return 1;
                        });

        cf.then(literal("help")
                .requires(source -> source.hasPermissionLevel(4))
                .executes(c -> {
                    sendCommendMessage(c.getSource().getPlayer(), "§l§2#########§l§aChmlFrpFabric帮助列表§l§2#########" + "\n" +
                            "§f/cf help  §e#获取帮助列表" + "\n" +
                            "§f/cf login [用户名] [密码]  §e#登录ChmlFrp" + "\n" +
                            "§f/cf logint [token]  §e#登录ChmlFrp" + "\n" +
                            "§f/cf user  §e#查看用户信息" + "\n" +
                            "§f/cf run  §e#启动映射服务" + "\n" +
                            "§f/cf stop  §e#关闭映射服务" + "\n" +
                            "§f/cf logout  §e#查看Frp输出" + "\n" +
                            "§f/cf autorun  §e#MC运行时自动启动隧道" + "\n" +
                            "§f/cf version  §e#查看模组版本");
                    return 1;
                }));
        cf.then(literal("login")
                .requires(c -> c.hasPermissionLevel(4))
                .executes(c -> {
                    return 1;
                }));

        cf.then(literal("logint")
                .requires(c -> c.hasPermissionLevel(4))
                .executes(c -> {
                    return 1;
                }));
        cf.then(literal("user")
                .requires(source -> source.hasPermissionLevel(4))
                .executes(c -> {
                    sendCommendMessage(c.getSource().getPlayer(), "§l§b<ChmlFrpINFO> " + "\n" +
                            "§f用户ID:" + AllMagic.AllUserInfo.getUserid() + "\n" +
                            "用户名:" + AllMagic.AllUserInfo.getUsername() + "\n" +
                            "邮箱:" + AllMagic.AllUserInfo.getEmail() + "\n" +
                            "QQ:" + AllMagic.AllUserInfo.getQq() + "\n" +
                            "用户组:" + AllMagic.AllUserInfo.getUsergroup() + "\n" +
                            "带宽配速:" + AllMagic.AllUserInfo.getBandwidth() + "\n" +
                            "隧道数量:" + AllMagic.AllUserInfo.getTunnel() + "\n" +
                            "Token:" + AllMagic.AllUserInfo.getToken() + "\n" +
                            "实名:" + AllMagic.AllUserInfo.getRealname() + "\n" +
                            "到期时间:" + AllMagic.AllUserInfo.getTerm() + "\n");
                    return 1;
                }));
        cf.then(literal("run")
                .requires(source -> source.hasPermissionLevel(4))
                .executes(c -> {

                    BetaMod BetaMod = new BetaMod();
                    try {
                        BetaMod.startFRPC();
                        sendCommendMessage(c.getSource().getPlayer(), "§l§b<ChmlFrpINFO> §fFrp服务已提交 ");
                        //client.getWindow().setTitle("Minecraft 1.20.1 ChmlFrp已提交");

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    return 1;
                }));
        cf.then(literal("stop")
                .requires(source -> source.hasPermissionLevel(4))
                .executes(c -> {
                    BetaMod.stopFRPC();
                    sendCommendMessage(c.getSource().getPlayer(), "§l§b<FrpINFO> §f" + "Frp服务已停止");
                    return 1;
                }));
        cf.then(literal("logout")
                .requires(source -> source.hasPermissionLevel(4))
                .executes(c -> {
                    sendCommendMessage(c.getSource().getPlayer(), "§l§b<FrpINFO> §a" + "\n" + AllMagic.FrpAllLogOut);
                    return 1;
                }));
        cf.then(literal("autorun")
                .requires(source -> source.hasPermissionLevel(4))
                .executes(c -> {
                    return 1;
                }));
        cf.then(literal("version")
                .requires(source -> source.hasPermissionLevel(4))
                .executes(c -> {
                    sendCommendMessage(c.getSource().getPlayer(), "§l§b<ChmlFrpINFO> §fChmlFrp-Fabric-Mod当前版本为 1.20.1-0.10.0");
                    return 1;
                }));
        dispatcher.register(cf);

    }

    public static int sendCommendMessage(PlayerEntity player, String Content) {
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