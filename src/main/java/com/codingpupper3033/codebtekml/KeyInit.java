package com.codingpupper3033.codebtekml;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

/**
 * Binds the key binds of the mod
 */
public final class KeyInit {
    public static KeyBinding openKMLMenuKeyBind;

    public static void init() {
        openKMLMenuKeyBind = registerKey("Open KMZ Menu", "BTE KML", Keyboard.KEY_PERIOD);
    }

    public static KeyBinding registerKey(String name, String category, int keycode) {
        KeyBinding key = new KeyBinding(name, keycode, category);
        ClientRegistry.registerKeyBinding(key);
        return key;
    }
}
