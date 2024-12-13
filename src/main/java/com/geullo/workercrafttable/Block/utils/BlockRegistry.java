package com.geullo.workercrafttable.Block.utils;

import com.geullo.workercrafttable.Block.*;
import com.geullo.workercrafttable.Block.City.*;
import com.geullo.workercrafttable.Item.ItemsRegiter;
import com.geullo.workercrafttable.Main;
import com.life.item.block.BlockBase;
import javafx.scene.control.Cell;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class BlockRegistry {
    public static final List<Block> REGISTER_BLOCKS = new ArrayList<>();
    public static final List<Item> REGISTER_BLOCKS_ITEM = new ArrayList<>();
    public static final FurnitureTable furnitureMakerTable = new FurnitureTable("furniture_maker_table", ItemsRegiter.workerCraftTableTab, Material.WOOD);
    public static final ShamanTable shamanTable = new ShamanTable("shaman_table", ItemsRegiter.workerCraftTableTab, Material.WOOD);
    public static final ShamanTable shamanTable1 = new ShamanTable("shaman_table_1", ItemsRegiter.workerCraftTableTab, Material.WOOD);
    public static final JewelerTable jewelerTable = new JewelerTable("jeweler_table", ItemsRegiter.workerCraftTableTab, Material.WOOD);
    public static final ArtistTable artistTable = new ArtistTable("artist_table", ItemsRegiter.workerCraftTableTab, Material.WOOD);
    public static final DesignerTable designerTable = new DesignerTable("designer_table", ItemsRegiter.workerCraftTableTab, Material.IRON);
    public static final SuperMarketTable supermarketTable = new SuperMarketTable("supermarket_table", ItemsRegiter.workerCraftTableTab, Material.IRON);
    public static final Cauldron cauldron = new Cauldron("cauldron", ItemsRegiter.workerCraftTableTab);
    public static final OakTong oakTong = new OakTong("oak_tong", ItemsRegiter.workerCraftTableTab);
    public static final PotStand potStand = new PotStand("pot_stand", ItemsRegiter.workerCraftTableTab);
    public static final AtmBlock atmBlock = new AtmBlock("atm", ItemsRegiter.workerCraftTableTab);
    public static final AtmBlock coin_change_machine_1 = new AtmBlock("coin_change_machine_1", ItemsRegiter.workerCraftTableTab);
    public static final AtmBlock coin_change_machine_2 = new AtmBlock("coin_change_machine_2", ItemsRegiter.workerCraftTableTab);
    public static final AtmBlock coin_change_machine_3 = new AtmBlock("coin_change_machine_3", ItemsRegiter.workerCraftTableTab);
    public static final FakeBlock fake_block = new FakeBlock();
    public static final Block num_1 = new NumBlock(1);
    public static final Block num_2 = new NumBlock(2);
    public static final Block num_3 = new NumBlock(3);
    public static final Block num_4 = new NumBlock(4);
    public static CreativeTabs cityTabs = new CreativeTabs("tabCity") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(city_block_1);
        }

        @Override
        public boolean hasSearchBar() {
            return true;
        }
    }.setBackgroundImageName("item_search.png");
    public static final CityBlock city_block_1 = new CityBlock("1");
    public static final BlockSlab city_slab_double_1 = new CityDoubleSlabBase("1",BlockRegistry.city_slab_half_1);
    public static final BlockSlab city_slab_half_1 = new CityHalfSlabBase("1",BlockRegistry.city_slab_half_1,BlockRegistry.city_slab_double_1);
    public static final Block city_stairs_1 = new CityStairBase("1", city_block_1.getDefaultState());
    public static final CityBlock city_block_2 = new CityBlock("2");
    public static final BlockSlab city_slab_double_2 = new CityDoubleSlabBase("2",BlockRegistry.city_slab_half_2);
    public static final BlockSlab city_slab_half_2 = new CityHalfSlabBase("2",BlockRegistry.city_slab_half_2,BlockRegistry.city_slab_double_2);
    public static final Block city_stairs_2 = new CityStairBase("2", city_block_2.getDefaultState());
    public static final CityBlock city_block_3 = new CityBlock("3");
    public static final BlockSlab city_slab_double_3 = new CityDoubleSlabBase("3",BlockRegistry.city_slab_half_3);
    public static final BlockSlab city_slab_half_3 = new CityHalfSlabBase("3",BlockRegistry.city_slab_half_3,BlockRegistry.city_slab_double_3);
    public static final Block city_stairs_3 = new CityStairBase("3", city_block_3.getDefaultState());
    public static final CityBlock city_block_4 = new CityBlock("4");
    public static final BlockSlab city_slab_double_4 = new CityDoubleSlabBase("4",BlockRegistry.city_slab_half_4);
    public static final BlockSlab city_slab_half_4 = new CityHalfSlabBase("4",BlockRegistry.city_slab_half_4,BlockRegistry.city_slab_double_4);
    public static final Block city_stairs_4 = new CityStairBase("4", city_block_4.getDefaultState());
    public static final CityBlock city_block_5 = new CityBlock("5");
    public static final BlockSlab city_slab_double_5 = new CityDoubleSlabBase("5",BlockRegistry.city_slab_half_5);
    public static final BlockSlab city_slab_half_5 = new CityHalfSlabBase("5",BlockRegistry.city_slab_half_5,BlockRegistry.city_slab_double_5);
    public static final Block city_stairs_5 = new CityStairBase("5", city_block_5.getDefaultState());
    public static final CityBlock city_block_6 = new CityBlock("6");
    public static final BlockSlab city_slab_double_6 = new CityDoubleSlabBase("6",BlockRegistry.city_slab_half_6);
    public static final BlockSlab city_slab_half_6 = new CityHalfSlabBase("6",BlockRegistry.city_slab_half_6,BlockRegistry.city_slab_double_6);
    public static final Block city_stairs_6 = new CityStairBase("6", city_block_6.getDefaultState());
    public static final CityBlock city_block_7 = new CityBlock("7");
    public static final BlockSlab city_slab_double_7 = new CityDoubleSlabBase("7",BlockRegistry.city_slab_half_7);
    public static final BlockSlab city_slab_half_7 = new CityHalfSlabBase("7",BlockRegistry.city_slab_half_7,BlockRegistry.city_slab_double_7);
    public static final Block city_stairs_7 = new CityStairBase("7", city_block_7.getDefaultState());
    public static final CityBlock city_block_8 = new CityBlock("8");
    public static final BlockSlab city_slab_double_8 = new CityDoubleSlabBase("8",BlockRegistry.city_slab_half_8);
    public static final BlockSlab city_slab_half_8 = new CityHalfSlabBase("8",BlockRegistry.city_slab_half_8,BlockRegistry.city_slab_double_8);
    public static final Block city_stairs_8 = new CityStairBase("8", city_block_8.getDefaultState());
    public static final CityBlock city_block_9 = new CityBlock("9");
    public static final BlockSlab city_slab_double_9 = new CityDoubleSlabBase("9",BlockRegistry.city_slab_half_9);
    public static final BlockSlab city_slab_half_9 = new CityHalfSlabBase("9",BlockRegistry.city_slab_half_9,BlockRegistry.city_slab_double_9);
    public static final Block city_stairs_9 = new CityStairBase("9", city_block_9.getDefaultState());
    public static final CityBlock city_block_10 = new CityBlock("10");
    public static final BlockSlab city_slab_double_10 = new CityDoubleSlabBase("10",BlockRegistry.city_slab_half_10);
    public static final BlockSlab city_slab_half_10 = new CityHalfSlabBase("10",BlockRegistry.city_slab_half_10,BlockRegistry.city_slab_double_10);
    public static final Block city_stairs_10 = new CityStairBase("10", city_block_10.getDefaultState());
    public static final CityBlock city_block_11 = new CityBlock("11");
    public static final BlockSlab city_slab_double_11 = new CityDoubleSlabBase("11",BlockRegistry.city_slab_half_11);
    public static final BlockSlab city_slab_half_11 = new CityHalfSlabBase("11",BlockRegistry.city_slab_half_11,BlockRegistry.city_slab_double_11);
    public static final Block city_stairs_11 = new CityStairBase("11", city_block_11.getDefaultState());
    public static final CityBlock city_block_12 = new CityBlock("12");
    public static final BlockSlab city_slab_double_12 = new CityDoubleSlabBase("12",BlockRegistry.city_slab_half_12);
    public static final BlockSlab city_slab_half_12 = new CityHalfSlabBase("12",BlockRegistry.city_slab_half_12,BlockRegistry.city_slab_double_12);
    public static final Block city_stairs_12 = new CityStairBase("12", city_block_12.getDefaultState());

    public static final CityModelBlock city_model_block_1 = new CityModelBlock("model_1");
    public static final CityModelBlock city_model_block_2 = new CityModelBlock("model_2");
    public static final Cellphones cell_phones = new Cellphones();


    public static void register(IForgeRegistry<Block> registry){
        REGISTER_BLOCKS.forEach(registry::register);
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry){
        REGISTER_BLOCKS_ITEM.forEach(registry::register);
    }
    public static void registerModels(){
        REGISTER_BLOCKS_ITEM.forEach(a->{
            Main.proxy.registerItemRenderer(a,0, a.getRegistryName().getResourcePath());
        });
    }
}
