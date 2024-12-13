package com.geullo.workercrafttable.Item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemWaterBucket extends ItemBucket {
    Block containedBlock;
    public ItemWaterBucket(Block containedBlockIn,String buckket) {
        super(containedBlockIn);
        setUnlocalizedName(buckket);
        maxStackSize = 32;
        this.containedBlock = containedBlockIn;
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        boolean flag = containedBlock == Blocks.AIR;
        if (getUnlocalizedName().equals("bucket")) return new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, flag);
        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(playerIn, worldIn, itemstack, raytraceresult);
        if (ret != null) return ret;

        if (raytraceresult == null)
        {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
        }
        else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
        {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
        }
        else
        {
            BlockPos blockpos = raytraceresult.getBlockPos();

            if (!worldIn.isBlockModifiable(playerIn, blockpos))
            {
                return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
            }
            else if (flag)
            {
                if (!playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack))
                {
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
                }
                else
                {
                    IBlockState iblockstate = worldIn.getBlockState(blockpos);
                    Material material = iblockstate.getMaterial();

                    if (material == Material.WATER && ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0)
                    {
                        worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                        playerIn.addStat(StatList.getObjectUseStats(this));
                        playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
                        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, this.fillBucket(itemstack, playerIn, Items.WATER_BUCKET));
                    }
                    else if (material == Material.LAVA && ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0)
                    {
                        playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL_LAVA, 1.0F, 1.0F);
                        worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                        playerIn.addStat(StatList.getObjectUseStats(this));
                        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, this.fillBucket(itemstack, playerIn, Items.LAVA_BUCKET));
                    }
                    else
                    {
                        return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
                    }
                }
            }
            else
            {
                boolean flag1 = worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
                BlockPos blockpos1 = flag1 && raytraceresult.sideHit == EnumFacing.UP ? blockpos : blockpos.offset(raytraceresult.sideHit);

                if (!playerIn.canPlayerEdit(blockpos1, raytraceresult.sideHit, itemstack))
                {
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
                }
                else if (this.tryPlaceContainedLiquid(playerIn, worldIn, blockpos1))
                {
                    if (playerIn instanceof EntityPlayerMP)
                    {
                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)playerIn, blockpos1, itemstack);
                    }

                    playerIn.addStat(StatList.getObjectUseStats(this));
                    return !playerIn.capabilities.isCreativeMode ? new ActionResult(EnumActionResult.SUCCESS, fillBucket(itemstack,playerIn,Items.BUCKET)) : new ActionResult(EnumActionResult.SUCCESS, itemstack);
                }
                else
                {
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
                }
            }
        }
    }

    private ItemStack fillBucket(ItemStack emptyBuckets, EntityPlayer player, Item fullBucket)
    {
        if (player.capabilities.isCreativeMode)
        {
            return emptyBuckets;
        }
        else
        {
            emptyBuckets.shrink(1);

            if (emptyBuckets.isEmpty())
            {
                return new ItemStack(fullBucket);
            }
            else
            {
                if (!player.inventory.addItemStackToInventory(new ItemStack(fullBucket)))
                {
                    player.dropItem(new ItemStack(fullBucket), false);
                }

                return emptyBuckets;
            }
        }
    }
}
