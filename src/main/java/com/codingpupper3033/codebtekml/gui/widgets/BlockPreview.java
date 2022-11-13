package com.codingpupper3033.codebtekml.gui.widgets;

import com.codingpupper3033.codebtekml.helpers.block.BlockNameConverter;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

import java.util.Map;

/**
 * Allows you to draw a preview of a block more effectively at different scales.
 * TODO Allow different sizes than 50x50 (auto scale from size)
 * @author Joshua Miller
 */
public class BlockPreview extends Gui {
    int x;
    int y;
    int width;
    int height;
    String blockName;

    private static final int[] itemSize = {16,16}; // Size of the default block preview.

    /**
     * Instantiates a new Block preview.
     *
     * @param x         x Position
     * @param y         y position
     * @param width     the width
     * @param height    the height
     * @param blockName the name of the block to display
     */
    public BlockPreview(int x, int y, int width, int height, String blockName) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.blockName = blockName;
    }

    /**
     * Instantiates a new Block preview.
     *
     * @param x         x Position
     * @param y         y position
     * @param width     the width
     * @param height    the height
     */
    public BlockPreview(int x, int y, int width, int height) {
        this(x, y, width, height, "");
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public void drawPreview(Minecraft mc) {
        GlStateManager.pushMatrix(); // Its own thing
        {
            Map.Entry<String, Integer> nameMetaPair = BlockNameConverter.getBlockNameAndMeta(blockName); // Split to block + meta
            Block block = Block.getBlockFromName(nameMetaPair.getKey()); // Get block
            ItemStack itemStack = new ItemStack(block,1,nameMetaPair.getValue()); // Get item (+meta)

            RenderHelper.enableGUIStandardItemLighting(); // Bright boi

            //Scale Block Size
            int scale = Math.min(width/itemSize[0], height/itemSize[1]);
            int blockWidth = itemSize[0]*scale;
            int blockHeight = itemSize[1]*scale;

            // Get Icon position
            int blockRelativeX = (width-blockWidth)/2;
            int blockRelativeY = (height-blockHeight)/2;

            // Move to icon position
            GlStateManager.translate(x+blockRelativeX, y+blockRelativeY, 0);

            GlStateManager.scale(scale,scale,1); //x3

            mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack,0,0);
        }
        GlStateManager.popMatrix();
    }
}
