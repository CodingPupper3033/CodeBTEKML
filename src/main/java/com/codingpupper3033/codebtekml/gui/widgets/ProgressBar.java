package com.codingpupper3033.codebtekml.gui.widgets;

import com.codingpupper3033.codebtekml.CodeBTEKMLMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * Simple Progress Bar
 * @author Joshua Miller
 */
public class ProgressBar extends Gui {
    ResourceLocation icons = new ResourceLocation(CodeBTEKMLMod.MODID, "textures/gui/loading.png");
    private static final int BAR_SIZE = 4;

    private float percentage ;
    private int x;
    private int y;
    private int w;
    private int h;

    public ProgressBar(int x, int y, int w, int h, float percentage) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.percentage = percentage;
    }

    public ProgressBar(int x, int y, int w, int h) {
        this(x, y, w, h, 0f);
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    // I honestly wish I could explain why this works... it is just black magic that somehow works
    public void drawProgress() {
        Minecraft.getMinecraft().renderEngine.bindTexture(icons);

        float widthBar = (percentage*(w-2*BAR_SIZE));
        float widthBarNothing = (w-2*BAR_SIZE)-widthBar;
        float heightBar = h - 2 * BAR_SIZE;

        // Corners
        for (int i = 0; i < 4; i++) {
            GlStateManager.pushMatrix();
            {
                int xi = (i%2);
                int widthAdd = w*xi;
                int shiftSideX = (2*(xi)-1)*BAR_SIZE/2;

                int xDraw = x+widthAdd-shiftSideX;

                int yi = i>>1;
                int heightAdd = h*yi;
                int shiftSideY = (2*(yi)-1)*BAR_SIZE/2;

                int yDraw = y+heightAdd-shiftSideY;

                int roti = (xi^yi)+(yi<<1);

                GlStateManager.translate(xDraw, yDraw, 0);
                GlStateManager.rotate(roti*90, 0,0,1);

                int texturey = 0;

                // Draw full?
                if ((percentage > 0 && xi == 0) || (percentage == 1)) {
                    texturey = BAR_SIZE;
                }

                drawTexturedModalRect(-BAR_SIZE/2,-BAR_SIZE/2, 0,texturey, BAR_SIZE, BAR_SIZE);
            }
            GlStateManager.popMatrix();


        }

        // Horizontal Sides
        for (int i = 0; i < 2; i++) {
            GlStateManager.pushMatrix();
            {
                int yi = i%2;
                int heightAdd = h*yi;
                int shiftSideY = (2*(yi)-1)*BAR_SIZE/2;

                int yDraw = y+heightAdd-shiftSideY;

                GlStateManager.translate(x+BAR_SIZE+widthBar/2, yDraw, 0);
                GlStateManager.rotate(i*180, 0,0,1);
                GlStateManager.scale(widthBar/BAR_SIZE,1,1);
                drawTexturedModalRect(-BAR_SIZE/2,-BAR_SIZE/2, BAR_SIZE,BAR_SIZE, BAR_SIZE, BAR_SIZE);
            }
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            {
                int yi = i%2;
                int heightAdd = h*yi;
                int shiftSideY = (2*(yi)-1)*BAR_SIZE/2;

                int yDraw = y+heightAdd-shiftSideY;

                GlStateManager.translate(x+w-BAR_SIZE-widthBarNothing/2, yDraw, 0);
                GlStateManager.rotate(i*180, 0,0,1);
                GlStateManager.scale(widthBarNothing/BAR_SIZE,1,1);
                drawTexturedModalRect(-BAR_SIZE/2,-BAR_SIZE/2, BAR_SIZE,0, BAR_SIZE, BAR_SIZE);
            }
            GlStateManager.popMatrix();
        }

        // Vertical Sides
        for (int i = 0; i < 2; i++) {
            GlStateManager.pushMatrix();
            {
                int xi = (i%2);
                int widthAdd = w*xi;
                int shiftSideX = (2*(xi)-1)*BAR_SIZE/2;

                int xDraw = x+widthAdd-shiftSideX;

                GlStateManager.translate(xDraw, y+h/2, 0);
                GlStateManager.rotate(i*180-90, 0,0,1);
                GlStateManager.scale(heightBar /BAR_SIZE,1,1);

                int texturey = 0;

                // Draw full?
                if ((percentage > 0 && xi == 0) || (percentage == 1)) {
                    texturey = BAR_SIZE;
                }

                drawTexturedModalRect(-BAR_SIZE/2,-BAR_SIZE/2, BAR_SIZE,texturey, BAR_SIZE, BAR_SIZE);
            }
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            {
                int yi = i%2;
                int heightAdd = h*yi;
                int shiftSideY = (2*(yi)-1)*BAR_SIZE/2;

                int yDraw = y+heightAdd-shiftSideY;

                GlStateManager.translate(x+w-BAR_SIZE-widthBarNothing/2, yDraw, 0);
                GlStateManager.rotate(i*180, 0,0,1);
                GlStateManager.scale(widthBarNothing/BAR_SIZE,1,1);
                drawTexturedModalRect(-BAR_SIZE/2,-BAR_SIZE/2, BAR_SIZE,0, BAR_SIZE, BAR_SIZE);
            }
            GlStateManager.popMatrix();
        }

        // Draw Middle
        GlStateManager.pushMatrix();
        {
            // Full
            GlStateManager.translate(x+BAR_SIZE,y+BAR_SIZE,0);
            GlStateManager.scale(widthBar/BAR_SIZE,heightBar/BAR_SIZE,0);

            drawTexturedModalRect(0,0, 2*BAR_SIZE,BAR_SIZE, BAR_SIZE, BAR_SIZE);
        }
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        {
            // Empty
            GlStateManager.translate(x+BAR_SIZE+widthBar,y+BAR_SIZE,0);
            GlStateManager.scale(widthBarNothing/BAR_SIZE,heightBar/BAR_SIZE,0);

            drawTexturedModalRect(0,0, 2*BAR_SIZE,0, BAR_SIZE, BAR_SIZE);
        }
        GlStateManager.popMatrix();

    }

    public void drawProgress(float percentage) {
        setPercentage(percentage);
        drawProgress();
    }
}
