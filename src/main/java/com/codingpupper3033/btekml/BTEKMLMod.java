package com.codingpupper3033.btekml;

import com.codingpupper3033.btekml.events.KeyPressed;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = BTEKMLMod.MODID, name = BTEKMLMod.NAME, version = BTEKMLMod.VERSION)
public class BTEKMLMod
{
    public static final String MODID = "btekml";
    public static final String NAME = "BTE KML Importer";
    public static final String VERSION = "1.0.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        KeyInit.init();
        MinecraftForge.EVENT_BUS.register(new KeyPressed());
    }
}
