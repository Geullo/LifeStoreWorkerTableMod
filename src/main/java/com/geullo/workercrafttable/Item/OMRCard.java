package com.geullo.workercrafttable.Item;

import com.geullo.workercrafttable.Item.Base.ItemBase;
import com.geullo.workercrafttable.Mh;
import com.geullo.workercrafttable.UI.OMRUI;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OMRCard extends ItemBase {
    public OMRCard() {
        super("omr_card");
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (playerIn.getHeldItemMainhand().getItem().equals(this)&&worldIn.isRemote)
            open();
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @SideOnly(Side.CLIENT)
    public void open(){
        Minecraft.getMinecraft().displayGuiScreen(new OMRUI(Mh.getInstance().lastOmr));
    }
    @Override
    public CreativeTabs getCreativeTab() {
        return ItemsRegiter.items;
    }
}