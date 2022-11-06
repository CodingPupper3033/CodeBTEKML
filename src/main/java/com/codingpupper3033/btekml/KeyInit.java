package com.codingpupper3033.btekml;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public final class KeyInit {
    public static KeyBinding openKMLMenuKeybind;

    public static void init() {
        openKMLMenuKeybind = registerKey("Open KMZ Menu", "BTE KML", Keyboard.KEY_PERIOD);
    }

    public static KeyBinding registerKey(String name, String category, int keycode) {
        KeyBinding key = new KeyBinding(name, keycode, category);
        ClientRegistry.registerKeyBinding(key);
        return key;
    }
}
