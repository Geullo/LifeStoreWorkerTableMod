package com.geullo.workercrafttable.Block;

import com.geullo.workercrafttable.Block.utils.BlockInit;
import com.geullo.workercrafttable.Block.utils.BlockRegistry;
import com.geullo.workercrafttable.Main;
import com.geullo.workercrafttable.PotStand.TilePotStand;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;


public class PotStand extends BlockInit implements ITileEntityProvider {
    public static final AxisAlignedBB POT_STAND = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public String name;

    public PotStand(String name, CreativeTabs tab) {
        super(Material.IRON);
        this.name = name;
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(tab);
        setDefaultState(blockState.getBaseState().withProperty(FACING,EnumFacing.NORTH));
        BlockRegistry.REGISTER_BLOCKS_ITEM.add(createItemBlock());
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return 1.5f;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TilePotStand) {
            InventoryHelper.dropInventoryItems(worldIn, pos, (TilePotStand) tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }
        super.breakBlock(worldIn, pos, state);

    }


    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TilePotStand) {
                try {
                    playerIn.openGui(Main.instacne, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
                } catch (NullPointerException e) {
                    Main.logger.log(Level.ERROR, " Null Pointer Exception from EntityPlayer#openGui()");
                }
                return true;
            }
        }
        else return true;
        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePotStand();
    }


    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING,EnumFacing.getDirectionFromEntityLiving(pos,placer));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING,EnumFacing.getFront(meta & 7));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex()+1;
    }
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this,new IProperty[]{FACING});
    }
    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos)&&worldIn.getBlockState(pos.add(0,-1,0)).getBlock().getItem(worldIn,pos,worldIn.getBlockState(pos.add(0,-1,0))).isItemEqual(new ItemStack(Blocks.CONCRETE,1,14));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add("§c빨간색 콘크리트§7위에만 설치가 가능합니다.");
        super.addInformation(stack, player, tooltip, advanced);
    }
}
