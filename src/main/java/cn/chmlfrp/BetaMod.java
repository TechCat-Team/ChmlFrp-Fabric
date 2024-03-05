package cn.chmlfrp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mojang.datafixers.TypeRewriteRule;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

public class BetaMod {

    public static void BetaMod() {
    }
    public void startFRPC() throws IOException, InterruptedException {

        Thread frpcThread = new Thread(() -> {
            String gameDirPath = FabricLoader.getInstance().getGameDir().toString();
            //gameDirPath = "E:\\Desktop\\";
            System.out.println("游戏目录路径: " + gameDirPath);
            File directory = new File("../../ChmlFrp");
            if (!directory.exists()) {
                // 尝试创建目录
                boolean isCreated = directory.mkdir();
                if (isCreated) {
                    System.out.println("Directory created: " + "ChmlFrp");
                } else {
                    System.out.println("Failed to create directory: " + "ChmlFrp");
                }
            } else {
                System.out.println("Directory already exists: " + "ChmlFrp");
            }
            String frpcPath = gameDirPath + "\\ChmlFrp\\chmlfabricfrpc";
            // 确保替换为你的frpc配置文件的实际路径
            String configPath = gameDirPath + "\\ChmlFrp\\chmlfabricfrpc.ini";

            // 构建命令行字符串
            String command = frpcPath + " -c " + configPath;

            // 执行命令
            Process process = null;
            try {
                process = Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // 读取并打印frpc的标准输出流（实时）
            BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while (true) {
                try {
                    if (!((line = outputReader.readLine()) != null)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("FRPC Output: " + line);
                AllMagic.FrpAllLogOut += line + "\n";
            }
            int exitCode = 0;
            try {
                exitCode = process.waitFor();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (exitCode != 0) {
                System.err.println("Failed to start frpc service. Exit code: " + exitCode);
            } else {
                System.out.println("frpc service started successfully.");
            }
        });
        frpcThread.start();
    }

    // 停止frpc服务
    public static void stopFRPC() {
        try {
            // 组合taskkill命令参数，-F表示强制终止，/IM后面跟的是进程名（包括扩展名）
            String command = "taskkill /F /IM " + "chmlfabricfrpc.exe";

            // 执行系统命令
            Process process = Runtime.getRuntime().exec(command);

            // 获取子进程的输出流和错误流，读取并处理它们，这里为了简单起见，直接关闭而不读取
            process.getInputStream().close();
            process.getErrorStream().close();

            // 等待命令执行完成
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Failed to kill process by name: frpc.exe");
            } else {
                System.out.println("Successfully killed process with name: frpc.exe");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
