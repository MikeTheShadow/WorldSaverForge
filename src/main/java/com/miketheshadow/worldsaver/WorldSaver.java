package com.miketheshadow.worldsaver;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = WorldSaver.MODID, name = WorldSaver.NAME, version = WorldSaver.VERSION)
public class WorldSaver {
    public static final String MODID = "worldsaver";
    public static final String NAME = "World Saver";
    public static final String VERSION = "1.0";

    public static Logger LOGGER;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(ChunkLoadHandler.class);
        LOGGER.info("Hello from init!");
    }
}
