package com.codingpupper3033.codebtekml.gui.widgets.buttons;

import com.codingpupper3033.codebtekml.CodeBTEKMLMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * Custom GUI Buttons specifically for the Connect Screen, but could be used elsewhere.
 * @author Joshua Miller
 */
public class IconButton extends GuiButton {
    private static final ResourceLocation buttonTextureLocation = new ResourceLocation(CodeBTEKMLMod.MODID,"textures/gui/connect_screen.png");
    private final ICON type;

    /// Types of icons able to be on the button
    public enum ICON {
        FILE(0,0,16,16),
        HELP(16,0,16,16);

        private final int x;
        private final int y;
        private final int w;
        private final int h;


        ICON(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
    }

    /**
     * Instantiates a new Icon button.
     *
     * @param type     icon on the button
     * @param buttonId the button id
     * @param x        the x Position
     * @param y        the y Position
     * @param widthIn  the width of the button
     * @param heightIn the height of the button
     */
    public IconButton(ICON type, int buttonId, int x, int y, int widthIn, int heightIn) {
        super(buttonId, x, y, widthIn, heightIn, "");
        this.type = type;
    }

    /**
     * New IconButton
     * @param type icon on the button
     * @param buttonId id
     * @param x x Position
     * @param y y position
     */
    public IconButton(ICON type, int buttonId, int x, int y) {
        this(type, buttonId, x, y, 20, 20);
    }
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        super.drawButton(mc, mouseX, mouseY, partialTicks);

        if (visible) {
            GlStateManager.pushMatrix();
            {
                //Scale
                int scale = Math.min(width/type.w, height/type.h);
                int iconWidth = type.w*scale;
                int iconHeight = type.h*scale;

                // Icon position
                int iconRelativeX = (width-iconWidth)/2;
                int iconRelativeY = (height-iconHeight)/2;

                // Go to Icon Position
                GlStateManager.translate(x+iconRelativeX, y+iconRelativeY, 0);

                mc.renderEngine.bindTexture(buttonTextureLocation);
                GlStateManager.color(1,1,1,1);
                GlStateManager.scale(scale,scale,1);
                drawTexturedModalRect(0, 0,type.x,type.y, type.w, type.h);
            }
            GlStateManager.popMatrix();
        }
    }
}
