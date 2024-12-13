package com.geullo.workercrafttable.Block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SuperMarketTable extends WorkerCraftingTable {
    public SuperMarketTable(String name, CreativeTabs tab,Material material) {
        super(name, tab,material);
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return 1.2f;
    }
}
