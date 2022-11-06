package com.codingpupper3033.btekml.events;

import com.codingpupper3033.btekml.KeyInit;
import com.codingpupper3033.btekml.gui.screens.GuiDrawKML;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class KeyPressed {
    @SubscribeEvent
    public void clientTick(ClientTickEvent event) {
        if (!KeyInit.openKMLMenuKeybind.isPressed()) return;

        if (Minecraft.getMinecraft().currentScreen == null) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiDrawKML(null));
        }
    }
}
