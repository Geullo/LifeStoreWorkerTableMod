package com.geullo.workercrafttable.Packet;

import com.geullo.workercrafttable.PacketList;
import com.geullo.workercrafttable.PacketMessage;
import com.geullo.workercrafttable.PotStand.TilePotStand;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CAddJobPoint implements IMessage {
    private String msg;
    private ItemStack item;

    public CAddJobPoint() {
    }

    public CAddJobPoint(String msg, ItemStack item) {
        this.msg = msg;
        this.item = item;
//        this.data = data;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        try {
            //("§-§+"+buckkets.size()+"/오크통 양조가 완료 되었습니다.>>"+buckkets.size()+"/"+ Item.getIdFromItem(makeItem.getItem())+":"+ makeItem.getItemDamage()+":"+ makeItem.getCount()))
            ByteBufUtils.writeUTF8String(buf, PacketList.ADD_JOB_POINT.recogCode+"/"+msg +">>1/" + (Item.getIdFromItem(item.getItem())) + ":" + item.getItemDamage() + ":" + item.getCount());
//            System.out.println(((closed ? 1 : 0) + "a" + (pos.getX()) + "a" + (pos.getY()) + "a" + (pos.getZ())).length());
//            buf.writeInt(closed?1:0);
//            buf.writeInt(pos.getX());
//            buf.writeInt(pos.getY());
//            buf.writeInt(pos.getZ());
//            System.out.println("789 " + buf.readableBytes());
//        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
//        buf.writeInt(bytes.length);
//        buf.writeBytes(bytes);
        }
        catch (Exception e) {
        e.printStackTrace();
    }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            this.msg = ByteBufUtils.readUTF8String(buf);
//            this.closed = a[0].equals("1");
//            this.pos = new BlockPos(Integer.parseInt(a[1]), Integer.parseInt(a[2]), Integer.parseInt(a[3]));
//            this.closed = buf.readBoolean();
//            this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
//            System.out.println("456 " + buf.readableBytes() + " / " + closed + " / " + pos.getX() + " / " + pos.getY() + " / " + pos.getZ());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

//        int len = buf.readInt();
//        data = buf.toString(buf.readerIndex(), len, StandardCharsets.UTF_8);
//        buf.readerIndex(buf.readerIndex() + len);
    }

    public static class CHandler implements IMessageHandler<CAddJobPoint, IMessage> {
        @Override
        public IMessage onMessage(CAddJobPoint message, MessageContext ctx) {
            // This code is executed on the server when a packet is received
            // 서버에서 실행되는 패킷
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> accessMessage(message, ctx));
            return null; // We don't need to return a response
        }

        public void accessMessage(CAddJobPoint message, MessageContext ctx) {
            PacketMessage.sendMessage(message.msg);
        }
    }
}
