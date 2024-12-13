package com.geullo.workercrafttable.proxy;

import com.geullo.workercrafttable.Events.Events;
import com.geullo.workercrafttable.Events.RegisterKeybind;
import com.geullo.workercrafttable.Events.RenderGameOverlay;
import com.geullo.workercrafttable.Main;
import com.geullo.workercrafttable.PacketHandler;
import com.geullo.workercrafttable.PacketMessage;
import com.geullo.workercrafttable.util.Reference;
import com.life.item.block.BlockInit;
import com.life.item.item.ItemInit;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("lifeStore1");
    public static RenderGameOverlay renderGameOverlay = new RenderGameOverlay();


    @Override
    public void preInit(){
        super.preInit();
        MinecraftForge.EVENT_BUS.register(renderGameOverlay);
    }

    @Override
    public void init() {
        super.init();
        NETWORK.registerMessage(PacketMessage.Handle.class, PacketMessage.class, 0, Side.CLIENT);
        ClientRegistry.registerKeyBinding(RegisterKeybind.showLeftTime);
        FMLCommonHandler.instance().bus().register(Events.getInstance());
        GameRegistry.addSmelting(BlockInit.aquamarine_ore,new ItemStack(ItemInit.aquamarine_ingot,1),3);
        GameRegistry.addSmelting(BlockInit.silver_ore,new ItemStack(ItemInit.iron_ingot,1),3);
        GameRegistry.addSmelting(BlockInit.ruby_ore,new ItemStack(ItemInit.ruby_ingot,1),3);
        GameRegistry.addSmelting(BlockInit.topaz_ore,new ItemStack(ItemInit.topaz_ingot,1),3);
    }

    @Override
    public void postInit() {
        super.postInit();
    }
    public void registerItemRenderer(Item item, int metadataValue, String itemId) {
        ModelLoader.setCustomModelResourceLocation(item,metadataValue,new ModelResourceLocation(Reference.MOD_ID+":"+itemId,"inventory"));
    }

}
