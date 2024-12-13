package com.geullo.workercrafttable.Item;

import com.geullo.coinchange.UI.FavorCouponUI;
import com.geullo.workercrafttable.Item.Base.ItemBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class LikabilityCoupon extends ItemBase {
    public LikabilityCoupon() {
        super("likability_coupon");
        setCreativeTab(ItemsRegiter.items);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("우클릭시 쿠폰을 사용할수 있습니다.");
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (playerIn.getHeldItemMainhand().getItem().equals(this)&&worldIn.isRemote)
            Minecraft.getMinecraft().displayGuiScreen(new FavorCouponUI(""));
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
