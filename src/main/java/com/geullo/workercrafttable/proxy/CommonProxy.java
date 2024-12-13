package com.geullo.workercrafttable.proxy;

import com.geullo.workercrafttable.Packet.HandlerPacket;
import com.life.item.block.BlockInit;
import com.life.item.item.ItemInit;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy
{
    public void preInit()  {
        HandlerPacket.registerMessages("workertablech");
    }

    public void init(){
        GameRegistry.addSmelting(BlockInit.aquamarine_ore,new ItemStack(ItemInit.aquamarine_ingot,1),3);
        GameRegistry.addSmelting(BlockInit.silver_ore,new ItemStack(ItemInit.iron_ingot,1),3);
        GameRegistry.addSmelting(BlockInit.ruby_ore,new ItemStack(ItemInit.ruby_ingot,1),3);
        GameRegistry.addSmelting(BlockInit.topaz_ore,new ItemStack(ItemInit.topaz_ingot,1),3);
    }

    public void postInit(){
    }

    public void registerItemRenderer(Item itemFromBlock, int i, String name) {
    }
}
