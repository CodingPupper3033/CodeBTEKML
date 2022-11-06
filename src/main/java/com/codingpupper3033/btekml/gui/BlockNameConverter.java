package com.codingpupper3033.btekml.gui;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.AbstractMap;
import java.util.Map;

public class BlockNameConverter {
    public static final String metaSplit = ":";

    public static AbstractMap.SimpleEntry<String, Integer> getBlockNameAndMeta(String blockName) {
        int meta = 0;
        if (blockName.contains(metaSplit)) { // name:meta
            String[] split = blockName.split(metaSplit);

            try {
                meta = Integer.parseInt(split[split.length-1].trim()); // Try converting the last bit to a meta

                // Found meta, so rename block name w/o state
                blockName = blockName.substring(0, blockName.lastIndexOf(metaSplit));
            } catch (NumberFormatException e) {

            }
        }

        return new AbstractMap.SimpleEntry<>(blockName, meta);
    }

    public static Map.Entry<String, Integer> getBlockNameAndMeta(ItemStack itemStack) {
        String name = Block.getBlockFromItem(itemStack.getItem()).getLocalizedName();
        int meta = itemStack.getMetadata();
        return new AbstractMap.SimpleEntry<>(name, meta);
    }

    public static String getBlockCombinedName(Map.Entry<String, Integer> blockNameAndMeta) {
        return blockNameAndMeta.getKey() + metaSplit + blockNameAndMeta.getValue();
    }
}
