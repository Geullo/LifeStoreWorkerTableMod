package com.geullo.workercrafttable.Block.City;

import com.geullo.workercrafttable.Block.utils.BlockRegistry;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CityHalfSlabBase extends CitySlabBase{
    public CityHalfSlabBase(String id, BlockSlab half,BlockSlab doubleSlab) {
        super("half_"+id, half);
        setCreativeTab(BlockRegistry.cityTabs);
        BlockRegistry.REGISTER_BLOCKS_ITEM.add(new ItemSlab(this,this,doubleSlab).setRegistryName(ul));
    }

    @Override
    public boolean isDouble() {
        return false;
    }

}
