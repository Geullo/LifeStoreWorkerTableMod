package com.geullo.workercrafttable.Oaktong;

import com.geullo.workercrafttable.Cauldron.RecipeCauldron;
import com.geullo.workercrafttable.PacketList;
import com.geullo.workercrafttable.PacketMessage;
import com.geullo.workercrafttable.util.Reference;
import com.geullo.workercrafttable.util.Render;
import com.geullo.workercrafttable.util.Utils;
import com.life.item.item.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiOakTong extends GuiContainer {
    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;
    private String name;

    private static ResourceLocation bg;
    private int lvl = 0;
    private TileOaktong oaktong;
    private ContainerOakTong containerOakTong;
    public void initLvl(int storelvl) {
        RecipeOakTong ro = RecipeOakTong.getInstance();
        ro.clearRecipe();
        this.lvl = storelvl;
        switch (storelvl) {
            case 6:
            case 5:
            case 4:
                ro.addRecipe(new ItemStack(ItemInit.fried_old_kimchi,1), new ItemStack(Items.DYE,1,3), new ItemStack(ItemInit.makgeolli,1), new ItemStack(ItemInit.makgeolli_jeongsik,1));
                ro.addRecipe(Items.SUGAR, ItemInit.rose_golden_ring, ItemInit.god_thing, new ItemStack(ItemInit.fish_tear,1));
                ro.addRecipe(ItemInit.luxury_rose_sac, Items.SUGAR, ItemInit.shangria, new ItemStack(ItemInit.flower_shangria,1));
            case 3:
                ro.addRecipe(Items.SUGAR, ItemInit.oran_fru, ItemInit.wine, new ItemStack(ItemInit.shangria,1));
                ro.addRecipe(new ItemStack(Items.SUGAR,1), new ItemStack(Items.DYE,1,3), new ItemStack(ItemInit.makgeolli,1), new ItemStack(ItemInit.coffee_makgeolli,1));
                ro.addRecipe(Items.SUGAR, ItemInit.beer, ItemInit.whiskey, new ItemStack(ItemInit.god_thing,1));
                ro.addRecipe(Items.SUGAR, ItemInit.lemon_fru, ItemInit.board_car, new ItemStack(ItemInit.lemon_drop,1));

            case 2:
                ro.addRecipe(Items.WATER_BUCKET, ItemInit.gra_fru, ItemInit.glass, new ItemStack(ItemInit.wine,1));
                ro.addRecipe(new ItemStack(Items.SUGAR,1), new ItemStack(Items.DYE,1, 3), new ItemStack(ItemInit.board_car,1), new ItemStack(ItemInit.choco_mat,1));
                ro.addRecipe(Items.SUGAR, Items.MILK_BUCKET, ItemInit.whiskey, new ItemStack(ItemInit.milk_punch,1));
                ro.addRecipe(new ItemStack(Items.SUGAR,1), new ItemStack(Items.DYE,1,4), new ItemStack(ItemInit.whiskey,1), new ItemStack(ItemInit.blue_hawaii,1));
        }
    }
    public GuiOakTong(ContainerOakTong inventorySlotsIn, TileOaktong oaktong,String name)
    {
        super(inventorySlotsIn);
        PacketMessage.sendMessage(PacketList.GET_STORE_LEVEL.recogCode);
        this.oaktong = oaktong;
        this.containerOakTong = inventorySlotsIn;
        initLvl(lvl);
        xSize = WIDTH;
        ySize = HEIGHT;
        this.name = name;
        oaktong.playerIn = containerOakTong.player;

        bg = new ResourceLocation(Reference.MOD_ID,"uitexture/oak_tong.png");
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if (keyCode == Keyboard.KEY_O) {
            lvl = lvl+1<=5?lvl+1:lvl;
            initLvl(lvl);
        }
        else if (keyCode == Keyboard.KEY_P) {
            initLvl(0);
        }
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

        if (oaktong.getProgress() > 0) {
            int percent = 100 - oaktong.getProgress() * 100 / TileOaktong.MAX_PROGRESS;
            this.drawTexturedModalRect(i+64, j + 6, 175, 169, Utils.percent(81,percent) + 1, 2);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public void handleKeyboardInput() throws IOException {
        if (getSlotUnderMouse()!=null&&oaktong.getProgress()>0&&getSlotUnderMouse().slotNumber<=4&&getSlotUnderMouse().slotNumber>=0&&Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindDrop.getKeyCode())){
            return;
        }
        super.handleKeyboardInput();
    }

    @Override
    protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
        super.handleMouseClick(slotIn, slotId, mouseButton, type);
        oaktong.markDirty();
    }

}
