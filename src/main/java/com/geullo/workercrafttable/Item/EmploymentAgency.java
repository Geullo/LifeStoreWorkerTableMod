package com.geullo.workercrafttable.Item;

import com.geullo.workercrafttable.Item.Base.ItemBase;
import net.minecraft.creativetab.CreativeTabs;

import javax.annotation.Nullable;

public class EmploymentAgency extends ItemBase {
    public EmploymentAgency(String name) {
        super(name+"_employment_agency");
        setMaxStackSize(64);
    }

    @Nullable
    @Override
    public CreativeTabs getCreativeTab() {
        return ItemsRegiter.items;
    }
}
