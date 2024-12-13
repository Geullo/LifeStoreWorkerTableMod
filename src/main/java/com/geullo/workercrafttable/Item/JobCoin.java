package com.geullo.workercrafttable.Item;

import com.geullo.workercrafttable.Item.Base.ItemBase;
import net.minecraft.creativetab.CreativeTabs;

import javax.annotation.Nullable;

public class JobCoin extends ItemBase {

    public JobCoin(String name) {
        super(name+"_job_coin");
    }

    @Nullable
    @Override
    public CreativeTabs getCreativeTab() {
        return ItemsRegiter.items;
    }
}
