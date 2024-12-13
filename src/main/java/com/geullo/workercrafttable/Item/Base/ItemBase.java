package com.geullo.workercrafttable.Item.Base;

import com.geullo.workercrafttable.Item.ItemsRegiter;
import com.geullo.workercrafttable.Item.utils.ICreativeTabbable;
import com.geullo.workercrafttable.Item.utils.IRegisterable;
import com.geullo.workercrafttable.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBase extends Item implements IRegisterable, ICreativeTabbable {
    protected  String name;

    public ItemBase(String name){
        this.name = name;
        updateRegistryAndLocalizedName(name);
    }
    public ItemBase(ToolMaterial material,String name){
        this.name = name;
    }

    @Override
    public ItemBase setCreativeTab(CreativeTabs tabs) {
        super.setCreativeTab(tabs);
        return this;
    }
    public ItemStack toItemStack() {
        return new ItemStack(this);
    }

    @Override
    public void registerItemModel() {
        Main.proxy.registerItemRenderer(this,0,name);
    }

    @Override
    public void updateRegistryAndLocalizedName(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
        ItemsRegiter.ITEMS.add(this);
    }
}
