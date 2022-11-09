package com.codingpupper3033.codebtekml.events;

import com.codingpupper3033.codebtekml.KeyInit;
import com.codingpupper3033.codebtekml.gui.screens.GuiDrawKML;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

/**
 * Will Respond to players pressing a key
 * @author Joshua Miller
 */
public class KeyPressed {
    @SubscribeEvent
    public void clientTick(ClientTickEvent event) {
        if (Minecraft.getMinecraft().currentScreen == null) { // Keystrokes that only work on the default game menu
            // Open KML Menu
            if (KeyInit.openKMLMenuKeybind.isPressed()) Minecraft.getMinecraft().displayGuiScreen(new GuiDrawKML(null));
        }
    }
}
