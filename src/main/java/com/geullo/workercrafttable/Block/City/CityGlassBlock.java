package com.geullo.workercrafttable.Block.City;

import com.geullo.workercrafttable.Block.utils.BlockInit;
import com.geullo.workercrafttable.Block.utils.BlockRegistry;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class CityGlassBlock extends BlockStainedGlass {
    public CityGlassBlock(String id) {
        super(Material.BARRIER);
        setRegistryName("life_city_block_"+id);
        setUnlocalizedName("life_city_block_"+id);
        setCreativeTab(BlockRegistry.cityTabs);
        BlockRegistry.REGISTER_BLOCKS.add(this);
        BlockRegistry.REGISTER_BLOCKS_ITEM.add(new ItemBlock(this).setRegistryName(getRegistryName()));
    }
}
