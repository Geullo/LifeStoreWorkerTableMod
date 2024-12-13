package com.geullo.workercrafttable.Block;

import com.geullo.workercrafttable.Block.utils.BlockInit;
import com.geullo.workercrafttable.Block.utils.BlockRegistry;
import com.geullo.workercrafttable.Main;
import com.geullo.workercrafttable.Table.TileTable;
import com.geullo.workercrafttable.util.SoundEffect;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;


public class WorkerCraftingTable extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public String name;
    private SoundEvent successSound;

    public WorkerCraftingTable(String name,CreativeTabs tab,Material material) {
        super(material);
        this.name = name;
        if (name.contains("_1")) this.name = name.replace("_1","");
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(tab);

        setDefaultState(blockState.getBaseState().withProperty(FACING,EnumFacing.NORTH));
        BlockRegistry.REGISTER_BLOCKS.add(this);
        BlockRegistry.REGISTER_BLOCKS_ITEM.add(createItemBlock());
    }
    public Item createItemBlock() {
        ItemBlock itemBlock = new ItemBlock(this);
        itemBlock.setRegistryName(getRegistryName());
        return itemBlock;
    }
    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return 1.5f;
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
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this,new IProperty[]{FACING});
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
            if (te instanceof TileTable) {
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
        return new TileTable(this);
    }
    public SoundEvent ga(String name){
        switch (name){
            case "tile.furniture_maker_table":
                return SoundEffect.FURNITURE_MAKER_CRAFT_COMPLETE;
            case "tile.shaman_table":
                return SoundEffect.SHAMAN_CRAFT_COMPLETE;
            case "tile.jeweler_table":
                return SoundEffect.JEWELER_CRAFT_COMPLETE;
            case "tile.artist_table":
                return SoundEffect.ARTIST_CRAFT_COMPLETE;
            case "tile.cauldron":
                return SoundEffect.CHEF_CRAFT_COMPLETE;
            case "tile.designer_table":
                return SoundEffect.DESIGNER_CRAFT_COMPLETE;
            case "tile.oak_tong":
                return SoundEffect.BARKEEPER_CRAFT_COMPLETE;
            case "tile.supermarket_table":
                return SoundEffect.SUPERMARKET_CRAFT_COMPLETE;

        }
        return SoundType.ANVIL.getFallSound();
    }
    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos)&&worldIn.getBlockState(pos.add(0,-1,0)).getBlock().getItem(worldIn,pos,worldIn.getBlockState(pos.add(0,-1,0))).isItemEqual(new ItemStack(Blocks.CONCRETE,1,14));
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add("§c빨간색 콘크리트 §7위에만 설치가 가능합니다.");
        super.addInformation(stack, player, tooltip, advanced);
    }
}
