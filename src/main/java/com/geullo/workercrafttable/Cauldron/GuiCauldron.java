package com.geullo.workercrafttable.Cauldron;

import com.geullo.workercrafttable.PacketList;
import com.geullo.workercrafttable.PacketMessage;
import com.geullo.workercrafttable.util.Reference;
import com.geullo.workercrafttable.util.Render;
import com.geullo.workercrafttable.util.Utils;
import com.life.item.item.ItemInit;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiCauldron extends GuiContainer {
    public static final int WIDTH = 176;
    public static final int HEIGHT = 172;
    private String name;

    private static ResourceLocation bg;
    private int lvl = 0;
    private TileCauldron cauldron;

    public void initLvl(int storelvl) {
        RecipeCauldron rc = RecipeCauldron.getInstance();
        rc.clearRecipe();
        this.lvl = storelvl;
        switch (storelvl) {
            case 5:
            case 6:
            case 4:
                rc.addRecipe(ItemInit.oak_food_table, ItemInit.bossam, new ItemStack(ItemInit.dding_grand_bossam,1));
                rc.addRecipe(ItemInit.chicken, ItemInit.beer, new ItemStack(ItemInit.chicken_beer,1));
                rc.addRecipe(ItemInit.cd2, ItemInit.fried_old_kimchi, new ItemStack(ItemInit.donut_fried_kimchi,1));
            case 3:
                rc.addRecipe(Items.WHEAT, Items.AIR, ItemInit.old_kimchi, Items.WATER_BUCKET, new ItemStack(ItemInit.fried_old_kimchi,1));
                rc.addRecipe(Items.AIR, Items.WHEAT, ItemInit.old_kimchi, Items.WATER_BUCKET, new ItemStack(ItemInit.fried_old_kimchi,1));
                rc.addRecipe(ItemInit.instance_kimchi, Items.EGG, Items.AIR, ItemInit.jmt_rice, new ItemStack(ItemInit.kimchi_rice,1));
                rc.addRecipe(ItemInit.instance_kimchi, Items.EGG, ItemInit.jmt_rice, Items.AIR, new ItemStack(ItemInit.kimchi_rice,1));
                rc.addRecipe(ItemInit.pepper, ItemInit.old_kimchi, Items.BEEF, Items.WATER_BUCKET, new ItemStack(ItemInit.pork_old_kimchi,1));
                rc.addRecipe(ItemInit.pork, ItemInit.instance_kimchi, Items.AIR, Items.AIR, new ItemStack(ItemInit.bossam,1));
                rc.addRecipe(Items.AIR, Items.AIR, ItemInit.pork, ItemInit.instance_kimchi, new ItemStack(ItemInit.bossam,1));
                rc.addRecipe(ItemInit.pepper, ItemInit.old_kimchi, Items.AIR, Items.WATER_BUCKET, new ItemStack(ItemInit.kimchi_soup,1));
                rc.addRecipe(ItemInit.pepper, ItemInit.old_kimchi, Items.WATER_BUCKET, Items.AIR, new ItemStack(ItemInit.kimchi_soup,1));
            case 2:
                rc.addRecipe(Items.BEEF, Items.EGG, Items.AIR, ItemInit.jmt_rice, new ItemStack(ItemInit.meat_rice,1));
                rc.addRecipe(Items.BEEF, Items.EGG, ItemInit.jmt_rice, Items.AIR, new ItemStack(ItemInit.meat_rice,1));
                rc.addRecipe(new ItemStack(ItemInit.instance_kimchi,1), ItemStack.EMPTY, new ItemStack(Items.FISH,1,0), new ItemStack(ItemInit.jmt_rice,1), new ItemStack(ItemInit.korean_meal,1));
                rc.addRecipe(ItemStack.EMPTY, new ItemStack(ItemInit.instance_kimchi,1), new ItemStack(Items.FISH,1,0), new ItemStack(ItemInit.jmt_rice,1), new ItemStack(ItemInit.korean_meal,1));
                rc.addRecipe(Items.WHEAT, Items.AIR, Items.CHICKEN, Items.WATER_BUCKET, new ItemStack(ItemInit.chicken,1));
                rc.addRecipe(Items.AIR, Items.WHEAT, Items.CHICKEN, Items.WATER_BUCKET, new ItemStack(ItemInit.chicken,1));
        }
    }
    public GuiCauldron(EntityPlayer player ,ContainerCauldron inventorySlotsIn, TileCauldron cauldron, String name)
    {
        super(inventorySlotsIn);
        PacketMessage.sendMessage(PacketList.GET_STORE_LEVEL.recogCode);
        xSize = WIDTH;
        ySize = HEIGHT;
        this.name = name;
        this.cauldron = cauldron;
        initLvl(lvl);
        bg = new ResourceLocation(Reference.MOD_ID,"uitexture/cauldron.png");
        cauldron.player = inventorySlotsIn.player;

    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        /*if (keyCode == Keyboard.KEY_O) {
            RecipeCauldron.getInstance().clearRecipe();
            lvl = lvl+1<=5?lvl+1:lvl;
            initLvl(lvl);
        }
        else if (keyCode == Keyboard.KEY_P) {
            RecipeCauldron.getInstance().clearRecipe();
            initLvl(0);
        }*/
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, 72 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();
        mc.getTextureManager().bindTexture(bg);
        Render.setColor(0xffffffff);
        drawTexturedModalRect(guiLeft,guiTop,0,0,xSize,ySize);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        if (cauldron.getProgress() > 0) {
            int percent = 100 - cauldron.getProgress() * 100 / TileCauldron.MAX_PROGRESS;
            this.drawTexturedModalRect(i+94, j + 15, 176, 0, Utils.percent(22,percent) + 1, 16);
        }
        if (cauldron.getFuelProgress() > 0) {
            for (int t=1;t<cauldron.getFuelProgress()+1;t++) {
                this.drawTexturedModalRect(i+getX(t), j + getY(t)/*(50-((cauldron.getFuelProgress()-1)*6))*/, 179, 32, 3, 3);
                if (t>TileCauldron.STEP_ONE_FUEL_PROGRESS) {
                    this.drawTexturedModalRect(i+getX(t), j + getY(t)/*(50-((cauldron.getFuelProgress()-1)*6))*/, 179, 35, 3, 3);
                }
            }
        }
    }

    private int getX(int lvl) {
        return lvl<=TileCauldron.STEP_ONE_FUEL_PROGRESS ?100+(6*(lvl<10?lvl:lvl-9)):100+(6*((lvl<28?lvl:lvl-9)-(TileCauldron.STEP_ONE_FUEL_PROGRESS)));
        /*switch (lvl) {
            case 1:
                return 106;
            case 2:
                return 112;
            case 3:
                return 118;
            case 4:
                return 32;
        }
        return -20;*/
    }

    private int getY(int lvl) {
        if (lvl<10||(lvl>TileCauldron.STEP_ONE_FUEL_PROGRESS &&lvl<28)) {
            return 58;
        }
        return 64;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }


    @Override
    protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
        super.handleMouseClick(slotIn, slotId, mouseButton, type);
        cauldron.markDirty();
    }

}
