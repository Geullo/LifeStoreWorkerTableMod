package com.geullo.workercrafttable;

import com.geullo.workercrafttable.Block.utils.BlockRegistry;
import com.geullo.workercrafttable.Item.ItemsRegiter;
import com.geullo.workercrafttable.Packet.HandlerPacket;
import com.geullo.workercrafttable.proxy.CommonProxy;
import com.geullo.workercrafttable.proxy.GuiHandler;
import com.geullo.workercrafttable.util.PotionInit;
import com.geullo.workercrafttable.util.Reference;
import com.geullo.workercrafttable.util.SoundEffect;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME)
public class Main {
    @Instance
    public static Main instacne;

    @SidedProxy(clientSide = Reference.CLIENTPROXY, serverSide = Reference.COMMONPROXY)
    public static CommonProxy proxy;
    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(Main.instacne,new GuiHandler());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }


    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            BlockRegistry.register(event.getRegistry());
            GuiHandler.registerTileEntity();
        }

        @SubscribeEvent
        public static void registerItem(RegistryEvent.Register<Item> event) {
            IForgeRegistry<Item> registry = event.getRegistry();
            ItemsRegiter.register(registry);
            BlockRegistry.registerItemBlocks(event.getRegistry());
            Items.WATER_BUCKET.setMaxStackSize(32);

        }

        @SubscribeEvent
        public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
            SoundEffect.registerSounds(event.getRegistry());
        }/*
        @SubscribeEvent
        public static void registerPotion(RegistryEvent.Register<Potion> event) {
            PotionInit.registerPotions();
        }
*/
        @SubscribeEvent
        public static void registerItems(ModelRegistryEvent event) {
            ItemsRegiter.registerModel();
            BlockRegistry.registerModels();
        }
    }
}
