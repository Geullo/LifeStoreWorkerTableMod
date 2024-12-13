package com.geullo.workercrafttable.Block.City;

import com.geullo.workercrafttable.Block.utils.BlockRegistry;
import com.geullo.workercrafttable.Item.ItemsRegiter;
import net.minecraft.block.BlockSlab;

public class CityDoubleSlabBase extends CitySlabBase{
    public CityDoubleSlabBase(String id, BlockSlab half) {
        super("double_"+id, half);
        setCreativeTab(BlockRegistry.cityTabs);
    }

    @Override
    public boolean isDouble() {
        return true;
    }
}
