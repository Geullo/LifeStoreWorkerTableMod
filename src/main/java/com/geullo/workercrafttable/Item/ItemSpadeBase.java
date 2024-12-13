package com.geullo.workercrafttable.Item;

import com.geullo.workercrafttable.Item.utils.ICreativeTabbable;
import com.geullo.workercrafttable.Item.utils.IRegisterable;
import com.geullo.workercrafttable.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class ItemSpadeBase extends ItemSpade implements IRegisterable, ICreativeTabbable {
    protected  String name;
    public ItemSpadeBase() {
        super(ToolMaterial.DIAMOND);
        setCreativeTab(ItemsRegiter.items);
        updateRegistryAndLocalizedName("sand_shovel");
        this.attackDamage = 1.5f+ToolMaterial.GOLD.getAttackDamage();
    }

    @Override
    public void registerItemModel() {
        Main.proxy.registerItemRenderer(this,0,name);
    }

    @Override
    public ItemSpadeBase setCreativeTab(CreativeTabs tabs) {
        super.setCreativeTab(tabs);
        return this;
    }

    public ItemStack toItemStack() {
        return new ItemStack(this);
    }


    @Override
    public void updateRegistryAndLocalizedName(String name) {
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
        ItemsRegiter.ITEMS.add(this);
    }
}
