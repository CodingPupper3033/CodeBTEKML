package com.codingpupper3033.btekml.gui.widgets;

import com.codingpupper3033.btekml.gui.BlockNameConverter;
import javafx.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

import java.util.Map;

import static com.codingpupper3033.btekml.gui.BlockNameConverter.getBlockNameAndMeta;

public class BlockPreview extends Gui {
    int x;
    int y;
    int width;
    int height;
    String blockName;

    private static int[] itemSize = {16,16};

    public BlockPreview(int x, int y, int width, int height, String blockName) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.blockName = blockName;
    }

    public BlockPreview(int x, int y, int width, int height) {
        this(x, y, width, height, "");
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public void drawPreview(Minecraft mc) {
        GlStateManager.pushMatrix();
        {
            Map.Entry<String, Integer> nameMetaPair = BlockNameConverter.getBlockNameAndMeta(blockName); // Split to block + meta
            Block block = Block.getBlockFromName(nameMetaPair.getKey()); // Get block
            ItemStack itemStack = new ItemStack(block,1,nameMetaPair.getValue()); // Get item (+meta)

            RenderHelper.enableGUIStandardItemLighting(); // Bright boi
            GlStateManager.translate(x, y, 0);
            GlStateManager.scale(3,3,1); //x3

            mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack,0,0);
        }
        GlStateManager.popMatrix();
    }
}
