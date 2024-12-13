package com.geullo.workercrafttable.Block.utils;

import com.geullo.workercrafttable.Item.ItemsRegiter;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class NumBlock extends BlockInit{
    public NumBlock(int num) {
        super(Material.WOOD);
        this.setRegistryName("num_"+num);
        setUnlocalizedName("num_"+num);
        setCreativeTab(ItemsRegiter.items);
        BlockRegistry.REGISTER_BLOCKS_ITEM.add(createItemBlock());
    }
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
