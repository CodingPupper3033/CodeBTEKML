package com.codingpupper3033.btekml.gui.buttons;

import com.codingpupper3033.btekml.BTEKMLMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class IconButton extends GuiButton {
    private static final ResourceLocation buttonTextureLocation = new ResourceLocation(BTEKMLMod.MODID,"textures/gui/connect_screen.png");
    private ICON type;

    public enum ICON {
        FILE(0,0,16,16),
        HELP(16,0,16,16);

        private final int x;
        private int y;
        private int w;
        private int h;


        ICON(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
    }

    public IconButton(ICON type, int buttonId, int x, int y) {
        super(buttonId, x, y, 20, 20, "");
        this.type = type;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        super.drawButton(mc, mouseX, mouseY, partialTicks);

        if (visible) {
            GlStateManager.pushMatrix();
            {
                mc.renderEngine.bindTexture(buttonTextureLocation);
                GlStateManager.color(1,1,1,1);
                drawTexturedModalRect(x+2, y+2,type.x,type.y, type.w, type.h);
            }
            GlStateManager.popMatrix();
        }
    }
}
