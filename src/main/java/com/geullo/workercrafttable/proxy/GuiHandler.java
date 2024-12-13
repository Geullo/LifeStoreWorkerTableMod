package com.geullo.workercrafttable.proxy;

import com.geullo.bank.UI.ATMUI;
import com.geullo.coinchange.CoinType;
import com.geullo.coinchange.UI.CoinChangeUI;
import com.geullo.workercrafttable.ATM.TileATM;
import com.geullo.workercrafttable.Cauldron.ContainerCauldron;
import com.geullo.workercrafttable.Cauldron.GuiCauldron;
import com.geullo.workercrafttable.Cauldron.TileCauldron;
import com.geullo.workercrafttable.Oaktong.ContainerOakTong;
import com.geullo.workercrafttable.Oaktong.GuiOakTong;
import com.geullo.workercrafttable.Oaktong.TileOaktong;
import com.geullo.workercrafttable.PotStand.ContainerPotStand;
import com.geullo.workercrafttable.PotStand.GuiPotStand;
import com.geullo.workercrafttable.PotStand.TilePotStand;
import com.geullo.workercrafttable.Table.ContainerTable;
import com.geullo.workercrafttable.Table.GameUI.*;
import com.geullo.workercrafttable.Table.TileTable;
import com.geullo.workercrafttable.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos =new BlockPos(x,y,z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileTable){
            String name = world.getBlockState(pos).getBlock().getUnlocalizedName().replace("_table","").replace("tile.","").replace("_1","");
            return new ContainerTable(player.inventory,world,pos,(TileTable) te,name);
        }
        else if (te instanceof TileOaktong)return new ContainerOakTong(player,player.inventory,world,pos,(TileOaktong) te);
        else if (te instanceof TileCauldron) return new ContainerCauldron(player,player.inventory,world,pos,(TileCauldron) te);
        else if (te instanceof TilePotStand) return new ContainerPotStand(player,player.inventory,world,pos,(TilePotStand) te);
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID!=0) return null;
        BlockPos pos = new BlockPos(x,y,z);
        TileEntity te = world.getTileEntity(pos);
        Block block = world.getBlockState(pos).getBlock();
        if (te instanceof TileTable) {
            String name = block.getUnlocalizedName().replace("_table","").replace("tile.","").replace("_1","");
            return name.equals("artist")? new MusicGameUI(new ContainerTable(player.inventory,world,pos,(TileTable) te,name),name,(TileTable) te): name.equals("designer")? new DesignGameUI(new ContainerTable(player.inventory,world,pos,(TileTable) te,name),name,(TileTable) te): name.equals("furniture_maker")? new MoksuGameUI(new ContainerTable(player.inventory,world,pos,(TileTable) te,name),name,(TileTable) te): name.equals("jeweler")? new SegongGameUI(new ContainerTable(player.inventory,world,pos,(TileTable) te,name),name,(TileTable) te): name.equals("shaman")? new TimingGameUI(new ContainerTable(player.inventory,world,pos,(TileTable) te,name),name,(TileTable) te): name.equals("supermarket")? new SuperGameUI(new ContainerTable(player.inventory,world,pos,(TileTable) te,name),name,(TileTable) te): new TimingGameUI(new ContainerTable(player.inventory,world,pos,(TileTable) te,name),name,(TileTable) te);
        }
        else if (te instanceof TileOaktong) {
            return new GuiOakTong(new ContainerOakTong(player,player.inventory,world,pos,(TileOaktong) te),(TileOaktong) te,block.getUnlocalizedName().replace("_table","").replace("tile.",""));
        }
        else if (te instanceof TileCauldron) {
            return new GuiCauldron(player,new ContainerCauldron(player,player.inventory,world,pos,(TileCauldron) te),(TileCauldron) te,block.getUnlocalizedName().replace("_table","").replace("tile.",""));
        }
        else if (te instanceof TilePotStand) {
            return new GuiPotStand(new ContainerPotStand(player,player.inventory,world,pos,(TilePotStand) te),(TilePotStand) te,block.getUnlocalizedName().replace("_table","").replace("tile.",""));
        }
        return null;
    }

    public static void registerTileEntity(){
        GameRegistry.registerTileEntity(TileTable.class, Reference.MOD_ID+"_crafting_table");
        GameRegistry.registerTileEntity(TileOaktong.class,Reference.MOD_ID+"_oaktong");
        GameRegistry.registerTileEntity(TileCauldron.class,Reference.MOD_ID+"_cauldron");
        GameRegistry.registerTileEntity(TilePotStand.class,Reference.MOD_ID+"_pot_stand");
        GameRegistry.registerTileEntity(TileATM.class,Reference.MOD_ID+"_atm");
    }
}
