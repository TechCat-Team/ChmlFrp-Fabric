package cn.chmlfrp;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;

import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import net.minecraft.client.MinecraftClient;



public class ChmlFrpFabricMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    //MinecraftClient client = MinecraftClient.getInstance();
    public static final Logger LOGGER = LoggerFactory.getLogger("chmlfrp-fabric-mod");

	@Override
	public void onInitialize() {
        BetaMod BetaMod = new BetaMod();

		/*
		CommandRegistrationCallback.EVENT.register(
				(dispatcher, registryAccess, environment) -> ChmlFrpCommend.register(dispatcher)
		);
		*/
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> ChmlFrpCommend.register(dispatcher)
        );
        /*
        try {
            BetaMod.startFRPC();
            //client.getWindow().setTitle("Minecraft 1.20.1 ChmlFrp已提交");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

         */


        ServerStartCallback.EVENT.register((server) -> {
            BetaMod.stopFRPC();
        });




    }
}