package cn.chmlfrp;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.*;
import net.fabricmc.fabric.api.event.world.WorldTickCallback;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;

import java.io.File;

public class ChmlFrpLoadData {
    public static void ChmlFrpLoadData() {
        File token = new File("../chmlfrp/token.ini");
    }
}
