package com.geullo.workercrafttable;

import com.geullo.workercrafttable.proxy.ClientProxy;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.nio.charset.StandardCharsets;

public class PacketMessage2 implements IMessage {
	public PacketMessage2(int guiid, BlockPos pos)
	{
		this.guiID = guiid;
		this.blockPos = pos;
	}

	private int guiID;
	private BlockPos blockPos;

	@Override
	public void fromBytes(ByteBuf buf)
	{
		guiID = buf.readInt();
		blockPos = BlockPos.fromLong(buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(guiID);
		buf.writeLong(blockPos.toLong());
	}

	public static class Handler implements IMessageHandler<PacketMessage2, IMessage>
	{

		@Override
		public IMessage onMessage(PacketMessage2 message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketMessage2 message, MessageContext ctx)
		{
			if(message.guiID == 0)
			{
			}
		}
	}
}