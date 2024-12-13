package com.geullo.workercrafttable.Block.City;

import com.geullo.workercrafttable.Block.utils.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class CitySlabBase extends BlockSlab {
    public Block half;
    public String ul;
    public static final PropertyEnum<Variant> VARIANT = PropertyEnum.<Variant>create("variant", Variant.class);

    public CitySlabBase(String id, BlockSlab half) {
        super(Material.BARRIER);
        this.ul = "life_city_slab_"+id;
        setUnlocalizedName(ul);
        setRegistryName(ul);
        setCreativeTab(BlockRegistry.cityTabs);
        this.useNeighborBrightness = !this.isDouble();

        IBlockState st = this.getBlockState().getBaseState().withProperty(VARIANT, Variant.DEFAULT);
        if (!this.isDouble()&&st.getValue(HALF)==EnumBlockHalf.TOP) st.withProperty(HALF, EnumBlockHalf.TOP);
        else if (!this.isDouble()&&st.getValue(HALF)==EnumBlockHalf.BOTTOM) st.withProperty(HALF,EnumBlockHalf.BOTTOM);
        this.half =  half;
        BlockRegistry.REGISTER_BLOCKS.add(this);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState st = this.getBlockState().getBaseState().withProperty(VARIANT, Variant.DEFAULT);
        if (!this.isDouble()) st = st.withProperty(HALF,meta !=0? EnumBlockHalf.TOP : EnumBlockHalf.BOTTOM);
        return st;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int m = 0;
        if (!this.isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP) m = 1;
        return m;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        if (!this.isDouble()) return new BlockStateContainer(this,new IProperty[] {VARIANT,HALF});
        else return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }


    @Override
    public String getUnlocalizedName(int meta) {
        return super.getUnlocalizedName();
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return VARIANT;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return Variant.DEFAULT;
    }

    public static enum Variant implements IStringSerializable {
        DEFAULT;
        @Override
        public String getName() {
            return "default";
        }
    }
}
