package com.geullo.workercrafttable.Block;

import com.geullo.workercrafttable.Block.utils.BlockInit;
import com.geullo.workercrafttable.Block.utils.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class AtmBlock extends BlockInit {
    public String name;
    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final AxisAlignedBB BOUNDING = new AxisAlignedBB(0.0d,0.0d,0d,1d,2.0d,1.0d);

    public AtmBlock(String name, CreativeTabs tab) {
        super(Material.BARRIER);
        this.name = name;
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(tab);
        setBlockUnbreakable();
        BlockRegistry.REGISTER_BLOCKS_ITEM.add(createItemBlock());
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
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING,EnumFacing.getDirectionFromEntityLiving(pos,placer));
    }


    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING,EnumFacing.getFront(meta & 7));
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return BOUNDING;
    }


    @Nullable
    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        return super.collisionRayTrace(blockState, worldIn, pos, start, end);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
    }

    @Nullable
    @Override
    protected RayTraceResult rayTrace(BlockPos pos, Vec3d start, Vec3d end, AxisAlignedBB boundingBox) {
        Vec3d vec3d = start.subtract((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
        Vec3d vec3d1 = end.subtract((double)pos.getX(), (double)pos.getY()+1, (double)pos.getZ());
        RayTraceResult raytraceresult = boundingBox.calculateIntercept(vec3d, vec3d1);
        return raytraceresult == null ? null : new RayTraceResult(raytraceresult.hitVec.addVector((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()), raytraceresult.sideHit, pos);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (!worldIn.isRemote) worldIn.setBlockState(pos.add(0,1,0),BlockRegistry.fake_block.getBlockState().getBaseState());
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        if (!worldIn.isRemote) worldIn.setBlockToAir(pos.add(0,1,0));
    }
}
