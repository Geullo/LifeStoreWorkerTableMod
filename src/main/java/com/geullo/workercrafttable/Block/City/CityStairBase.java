package com.geullo.workercrafttable.Block.City;

import com.geullo.workercrafttable.Block.utils.BlockRegistry;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class CityStairBase extends BlockStairs {
    public String ul;
    public CityStairBase(String id, IBlockState modelState) {
        super(modelState);
        this.ul = "life_city_stairs_"+id;
        setUnlocalizedName(ul);
        setRegistryName(ul);
        setCreativeTab(BlockRegistry.cityTabs);
        BlockRegistry.REGISTER_BLOCKS.add(this);
        BlockRegistry.REGISTER_BLOCKS_ITEM.add(createItemBlock());
    }
    public Item createItemBlock() {
        ItemBlock itemBlock = new ItemBlock(this);
        itemBlock.setRegistryName(getRegistryName());
        return itemBlock;
    }
}
