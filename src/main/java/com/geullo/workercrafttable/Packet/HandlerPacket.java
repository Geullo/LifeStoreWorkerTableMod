package com.geullo.workercrafttable.Packet;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashMap;

public class HandlerPacket {
//    public static HashMap<String,SimpleNetworkWrapper> PKINSTANCES = new HashMap<>();
    public static SimpleNetworkWrapper INSTANCE;

    private static int ID = 1;
    private static int nextID() {
        return ID++;
    }
    public static void registerMessages(String channel) {
//        SimpleNetworkWrapper networks = NetworkRegistry.INSTANCE.newSimpleChannel(channel);
//        PKINSTANCES.put(channel,networks);
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channel);
        INSTANCE.registerMessage(SPotStandPacket.CHandler.class, SPotStandPacket.class,nextID(), Side.SERVER);
        INSTANCE.registerMessage(CAddJobPoint.CHandler.class, CAddJobPoint.class,nextID(), Side.CLIENT);
    }
    public static void sendToServer(boolean a , BlockPos pos) {
        INSTANCE.sendToServer(new SPotStandPacket(a,pos));
    }
    public static void sendTo(String msg, ItemStack item, EntityPlayerMP player) {
        INSTANCE.sendTo(new CAddJobPoint(msg,item),player);
    }
}
