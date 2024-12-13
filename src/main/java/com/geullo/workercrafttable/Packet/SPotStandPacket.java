package com.geullo.workercrafttable.Packet;

import com.geullo.workercrafttable.PotStand.TilePotStand;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPotStandPacket implements IMessage {
    //    private String data;
    private boolean closed;
    private BlockPos pos;

    public SPotStandPacket() {
    }

    public SPotStandPacket(boolean closed, BlockPos pos) {
        this.closed = closed;
        this.pos = pos;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        try {
            ByteBufUtils.writeUTF8String(buf, (closed ? 1 : 0) + "a" + (pos.getX()) + "a" + (pos.getY()) + "a" + (pos.getZ()));
        }
        catch (Exception e) {
        e.printStackTrace();
    }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            String[] a = ByteBufUtils.readUTF8String(buf).split("a");
            this.closed = a[0].equals("1");
            this.pos = new BlockPos(Integer.parseInt(a[1]), Integer.parseInt(a[2]), Integer.parseInt(a[3]));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class CHandler implements IMessageHandler<SPotStandPacket, IMessage> {
        @Override
        public IMessage onMessage(SPotStandPacket message, MessageContext ctx) {
            // This code is executed on the server when a packet is received
            // 서버에서 실행되는 패킷
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> accessMessage(message, ctx));
            return null; // We don't need to return a response
        }

        public void accessMessage(SPotStandPacket message, MessageContext ctx) {
            TileEntity tileEntity = ctx.getServerHandler().player.getServerWorld().getTileEntity(message.pos);
            if (tileEntity == null) return;
            if (tileEntity instanceof TilePotStand) {
                TilePotStand tp = (TilePotStand) tileEntity;
                boolean a = message.closed;
                tp.setClosed(a);
                tp.markDirty();
                tp.setClientClosed(a);
                tp.markDirty();
                tp.setTimer(a?TilePotStand.TIMER_INITIALI:0);
                tp.markDirty();
            }
        }
    }
}
