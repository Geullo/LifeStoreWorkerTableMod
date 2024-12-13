package com.geullo.workercrafttable.Item;

import com.geullo.bank.Packet;
import com.geullo.workercrafttable.Item.Base.ItemBase;
import com.geullo.workercrafttable.PacketList;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BusinessCard extends ItemBase {
    public BusinessCard(String name) {
        super(name+"_business_card");
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (playerIn.getHeldItemMainhand().getItem().equals(this)&&worldIn.isRemote) {
            open();
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @SideOnly(Side.CLIENT)
    public void open() {
        Packet.sendMessage(PacketList.ADD_PHONE_BOOK.recogCode+"/"+(transYDTV(getUnlocalizedName().replace("_business_card",""))));
    }

    @Nullable
    @Override
    public CreativeTabs getCreativeTab() {
        return ItemsRegiter.items;
    }

    protected String transYDTV(String nick) {
        return "d7297".equalsIgnoreCase(nick) ? "양띵" :
                "samsik23".equalsIgnoreCase(nick) ? "삼식" :
                        "rutaey".equalsIgnoreCase(nick) ? "루태" :
                                "huchu95".equalsIgnoreCase(nick) ? "후추" :
                                        "konG7".equalsIgnoreCase(nick) ? "콩콩" :
                                                "noonkkob".equalsIgnoreCase(nick) ? "눈꽃" :
                                                        "daju_".equalsIgnoreCase(nick) ? "다주" :
                                                                "seoneng".equalsIgnoreCase(nick) ? "서넹" : nick;
    }
}
