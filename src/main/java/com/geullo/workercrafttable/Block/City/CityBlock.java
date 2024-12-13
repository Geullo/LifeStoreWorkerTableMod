package com.geullo.workercrafttable.Block.City;

import com.geullo.workercrafttable.Block.utils.BlockInit;
import com.geullo.workercrafttable.Block.utils.BlockRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class CityBlock extends BlockInit {
    public CityBlock(String id) {
        super(Material.BARRIER);
        setRegistryName("life_city_block_"+id);
        setUnlocalizedName("life_city_block_"+id);
        setCreativeTab(BlockRegistry.cityTabs);
        BlockRegistry.REGISTER_BLOCKS.add(this);
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
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    protected boolean canSilkHarvest()
    {
        return true;
    }
}
