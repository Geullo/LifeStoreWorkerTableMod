package com.geullo.workercrafttable.Block;

import com.geullo.workercrafttable.Block.utils.BlockRegistry;
import com.geullo.workercrafttable.Item.ItemsRegiter;
import net.minecraft.block.BlockSand;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class SandBlock extends BlockSand {
    public SandBlock() {
        setUnlocalizedName("sand");
        setRegistryName("minecraft:sand");
        setHardness(0.5F);setSoundType(SoundType.SAND);
    }
    public Item createItemBlock() {
        ItemBlock itemBlock = new ItemBlock(this);
        itemBlock.setRegistryName(getRegistryName());
        return itemBlock;
    }
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(Blocks.GLASS);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        if (stack.getItem().equals(ItemsRegiter.spade_sand)) {
        }
    }
}
