package com.geullo.workercrafttable.Block.utils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import java.util.Random;

public class BlockInit extends Block {
    public BlockInit(Material materialIn) {
        super(materialIn);
        BlockRegistry.REGISTER_BLOCKS.add(this);
    }

    @Override
    public Block setUnlocalizedName(String name) {
        return super.setUnlocalizedName(name);
    }

    public Item createItemBlock() {
        ItemBlock itemBlock = new ItemBlock(this);
        itemBlock.setRegistryName(getRegistryName());
        return itemBlock;
    }
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }
}
