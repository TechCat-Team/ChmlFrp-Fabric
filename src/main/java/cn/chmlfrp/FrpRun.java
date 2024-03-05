package cn.chmlfrp;

import java.io.*;

public class FrpRun {
    Runtime RunFrp = Runtime.getRuntime();
    public void FrpRun(String FrpcFile , String FrpcIniFile) throws IOException {

        Process FrpProcess = RunFrp.exec("cmd /c start " + FrpcFile + " -c " + FrpcIniFile);
        Thread outputReader = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(FrpProcess.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    //AllMagic.FrpLogOut = "";
                    //AllMagic.FrpLogOut = "§l§b<FrpOut-INFO> " + line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // 开启读取输出流的线程
        outputReader.start();

        Thread errorReader = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(FrpProcess.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    //AllMagic.FrpLogOut = "";
                    //AllMagic.FrpLogOut = "§l§c<FrpOut-INFO> " + line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        errorReader.start();

    }
    public void FrpLogOut(String FrpLogOut) {
        AllMagic.FrpAllLogOut += FrpLogOut;
    }
    public void FrpStop() {

    }
}
