package com.geullo.workercrafttable.Item;

import com.geullo.workercrafttable.Item.Base.ItemBase;
import net.minecraft.creativetab.CreativeTabs;

import javax.annotation.Nullable;

public class VillageToken extends ItemBase {

    public VillageToken(String name) {
        super(name+"_village_token");
    }

    @Nullable
    @Override
    public CreativeTabs getCreativeTab() {
        return ItemsRegiter.items;
    }
}
