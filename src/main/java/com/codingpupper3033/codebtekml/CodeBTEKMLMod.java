package com.codingpupper3033.codebtekml;

import com.codingpupper3033.codebtekml.events.KeyPressed;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

/**
 * The mod itself
 * @author Joshua Miller
 */
@Mod(modid = CodeBTEKMLMod.MODID, name = CodeBTEKMLMod.NAME, version = CodeBTEKMLMod.VERSION)
public class CodeBTEKMLMod
{
    public static final String MODID = "codebtekml";
    public static final String NAME = "BTE KML Importer";
    public static final String VERSION = "1.0.2";

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
