package com.geullo.workercrafttable.Item.utils;

import net.minecraft.creativetab.CreativeTabs;

public interface ICreativeTabbable<T> {
    T setCreativeTab(CreativeTabs tabs);
}
