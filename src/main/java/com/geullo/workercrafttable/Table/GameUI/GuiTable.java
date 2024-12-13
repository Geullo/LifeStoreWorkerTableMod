package com.geullo.workercrafttable.Table.GameUI;

import com.geullo.workercrafttable.PacketList;
import com.geullo.workercrafttable.PacketMessage;
import com.geullo.workercrafttable.Table.ContainerTable;
import com.geullo.workercrafttable.Table.ContainerTable;
import com.geullo.workercrafttable.Table.TileTable;
import com.geullo.workercrafttable.util.*;
import com.life.item.item.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.recipebook.GuiRecipeBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuiTable extends GuiContainer {
    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;
    protected double[] btnSize = new double[2],btnPos = new double[2],
            bgSize = new double[2],bgPos = new double[2];
    protected GuiRecipeBook recipeBook;
    protected List<Item> denyCraftItem = new ArrayList<>();
    protected List<Item> minigameItem = new ArrayList<>();
    protected List<Item> notEarnJobPointItem = new ArrayList<>();
    protected String name;
    protected TileTable table;
    private boolean craftAllowed = true;
    protected String path = "games/";
    public ContainerTable containerTable;
    protected boolean buttonEnabled,btnClicked=false,buttonDrawed=true;
    public boolean gameVisible = false,gameCleared = false;


    protected static ResourceLocation bg;

    public void setGameCleared(boolean gameCleared) {
        this.gameCleared = gameCleared;
    }

    public void denyItemAdd(int level) {
        denyCraftItem.clear();
        minigameItem.add(ItemInit.egg_bread_bundle);
        minigameItem.add(ItemInit.chocolate_milk_bundle);
        minigameItem.add(ItemInit.hot_bread_bundle);
        minigameItem.add(ItemInit.chocolate_bread_bundle);
        minigameItem.add(ItemInit.hot_noodle_box);
        minigameItem.add(ItemInit.dding_seal_box);
        minigameItem.add(ItemInit.hot_bread_box);
        minigameItem.add(ItemInit.chocolate_bread_box);
        minigameItem.add(ItemInit.hot_noodle_edition);
        minigameItem.add(ItemInit.charm_box);
        minigameItem.add(ItemInit.hot_kimchi_box);
        switch (level) {
            case 1:
                denyCraftItem.add(ItemInit.egg_bread_bundle);
                denyCraftItem.add(ItemInit.chocolate_milk_bundle);
                denyCraftItem.add(ItemInit.hot_bread_bundle);
                denyCraftItem.add(ItemInit.chocolate_bread_bundle);
            case 2:
                denyCraftItem.add(ItemInit.hot_noodle_box);
                denyCraftItem.add(ItemInit.dding_seal_box);
                denyCraftItem.add(ItemInit.hot_bread_box);
                denyCraftItem.add(ItemInit.chocolate_bread_box);
            case 3:
                denyCraftItem.add(ItemInit.hot_noodle_edition);
                denyCraftItem.add(ItemInit.charm_box);
                denyCraftItem.add(ItemInit.hot_kimchi_box);
                break;
        }
    }

    public GuiTable(ContainerTable inventorySlotsIn, String name, TileTable table)
    {
        super(inventorySlotsIn);
        PacketMessage.sendMessage(PacketList.GET_STORE_LEVEL.recogCode);
        xSize = WIDTH;
        ySize = HEIGHT;
        this.name = name;
        this.containerTable = inventorySlotsIn;
        this.table = table;
        bg = new ResourceLocation(Reference.MOD_ID,"uitexture/"+name+"_crafting_table.png");
        this.recipeBook = new GuiRecipeBook();
        notEarnJobPointItem.add(ItemInit.noodle);
        notEarnJobPointItem.add(ItemInit.bread);
        notEarnJobPointItem.add(ItemInit.chocolate);
        notEarnJobPointItem.add(ItemInit.plastic_bag);
    }

    @Override
    public void initGui() {
        super.initGui();
        this.recipeBook.func_194303_a(WIDTH,HEIGHT,mc,false,((ContainerTable)this.inventorySlots).crafting);
        if (buttonDrawed) {
            btnSize[0] = WIDTH / 3.8;
            btnSize[1] = HEIGHT / 4.5 / 2.35;
            btnPos[0] = ((width / 2d) + (btnSize[0] / 2.24));
            btnPos[1] = ((height / 2d) - (btnSize[1] * 1.48));
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();
        mc.getTextureManager().bindTexture(bg);
        Render.setColor(0xffffffff);
        drawTexturedModalRect(guiLeft,guiTop,0,0,xSize,ySize);
        GlStateManager.pushMatrix();
        if (buttonDrawed) {
            Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "uitexture/button.png"));
            buttonEnabled = inventorySlots.getSlot(0).getHasStack();
            if (buttonEnabled) {
                Render.setColor(0xffffffff);
                Render.drawTexturedRect(btnPos[0], btnPos[1], btnSize[0], btnSize[1]);
                if (Utils.between(mouseX, mouseY, btnPos[0], btnPos[1], btnSize[0], btnSize[1])&&!gameVisible) {
                    Render.setColor(0x8083AEFF);
                    Render.drawTexturedRect(btnPos[0], btnPos[1], btnSize[0], btnSize[1]);
                }
            } else {
                Render.setColor(0xffffffff);
                Render.drawTexturedRect(btnPos[0], btnPos[1], btnSize[0], btnSize[1]);
                Render.setColor(0x80000000);
                Render.drawTexturedRect(btnPos[0], btnPos[1], btnSize[0], btnSize[1]);
            }
        }
        GlStateManager.popMatrix();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(I18n.format("container.crafting"), 28, 6, 4210752);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Item item = ((ContainerTable) this.inventorySlots).craftResult.getStackInSlot(0).getItem();
        if (
                (Objects.requireNonNull(Item.REGISTRY.getNameForObject(item)).getResourceDomain().equals("minecraft"))
                        || (item.getCreativeTab() != null && !item.getCreativeTab().getTabLabel().toLowerCase().contains(name))
                        || (!denyCraftItem.isEmpty()&&denyCraftItem.contains(item)))
        {
            if (name.equals("jeweler")&&item.equals(ItemInit.rosegold_ingot)) craftAllowed = true;
            else {
                ((ContainerTable) this.inventorySlots).craftResult.setInventorySlotContents(0, new ItemStack(Item.getItemById(0)));
                craftAllowed = false;
            }
        }
        else craftAllowed = true;
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (!gameVisible) renderHoveredToolTip(mouseX, mouseY);
        else {
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableDepth();
            drawMiniGame(partialTicks, mouseX, mouseY);
            GlStateManager.enableDepth();
        }
    }

    @Override
    protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {
        if (gameVisible) return false;
        return super.isPointInRegion(rectX, rectY, rectWidth, rectHeight, pointX, pointY);
    }

    protected void drawMiniGame(float partialTicks,int mouseX,int mouseY) {
        drawDefaultBackground();
    }

    protected void keyType(int keyCode) throws IOException {}
    protected void MiniGameClose() {
        initGame();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (gameVisible && keyCode == Keyboard.KEY_ESCAPE) {
            gameVisible = false;
            btnClicked = false;
            MiniGameClose();
            return;
        }
        else{
            keyType(keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
        if (!craftAllowed&&slotId==0) return;
        if (slotId==0&&btnClicked&&buttonDrawed){
            super.handleMouseClick(slotIn, slotId, mouseButton, type);
        }
        else if (!btnClicked) {
            super.handleMouseClick(slotIn, slotId, mouseButton, type);
        }
    }

    @Override
    public void handleInput() throws IOException {
        if (getSlotUnderMouse()!=null&&!craftAllowed&&getSlotUnderMouse().slotNumber==0) return;
        super.handleInput();
    }

    protected void failedGame() throws IOException
    {
        initGame();
    }

    protected void initGame(){
        btnClicked = false;
        gameVisible = false;
        gameCleared = false;
    }
    protected void successGame() throws IOException {
        gameCleared = true;
        mouseClicked(inventorySlots.getSlot(0).xPos+1, inventorySlots.getSlot(0).yPos+1, 0);
    }
    protected void buttonClick() {
        gameVisible = true;
        btnClicked = true;
        buttonEnabled = false;
    }

    @Nullable
    @Override
    public Slot getSlotUnderMouse() {
        if (gameCleared) return inventorySlots.getSlot(0);
        return super.getSlotUnderMouse();
    }

    protected void onCraft(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (gameCleared) {
            this.handleMouseClick(getSlotUnderMouse(), 0, mouseButton, ClickType.QUICK_MOVE);
            gameCleared = false;
            send();
            initGame();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (Utils.between(mouseX, mouseY, btnPos[0], btnPos[1], btnSize[0], btnSize[1]) && buttonEnabled && !btnClicked && buttonDrawed) {
            if (name.equals("supermarket")) {
                Item item = ((ContainerTable) this.inventorySlots).craftResult.getStackInSlot(0).getItem();
                if (minigameItem.contains(item)) buttonClick();
                else {
                    gameVisible = false;
                    btnClicked = true;
                    buttonEnabled = false;
                    successGame();
                }
            }
            else {
                buttonClick();
                return;
            }
            /*
            if (!name.equals("supermarket")) {
                buttonClick();
                return;
            }
            else {
                gameVisible = false;
                btnClicked = true;
                buttonEnabled = false;
                successGame();
            }*/
            return;
        }
        if (getSlotUnderMouse() != null && getSlotUnderMouse().slotNumber == 0&&buttonDrawed) {
            if (!btnClicked) return;
            if (gameCleared) {
                onCraft(mouseX, mouseY, mouseButton);
                return;
            }
        }
        if (btnClicked&&buttonDrawed) return;
        /*if (!buttonDrawed&&getSlotUnderMouse() != null && getSlotUnderMouse().slotNumber == 0&&name.equals("supermarket")) {if (!notEarnJobPointItem.contains(getSlotUnderMouse().getStack().getItem())) send();}*/
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @SideOnly(Side.CLIENT)
    protected void send() {
        Minecraft.getMinecraft().getSoundHandler().playSound(getSound(name));
    }
    @SideOnly(Side.CLIENT)
    protected ISound getSound(String name) {
        switch (name) {
            case "furniture_maker":
                return Sound.getSound(SoundEffect.FURNITURE_MAKER_CRAFT_COMPLETE, SoundCategory.BLOCKS,0.5f,1f);
            case "supermarket":
                return Sound.getSound(SoundEffect.SUPERMARKET_CRAFT_COMPLETE, SoundCategory.BLOCKS,0.5f,1f);
            case "jeweler":
                return Sound.getSound(SoundEffect.JEWELER_CRAFT_COMPLETE, SoundCategory.BLOCKS,0.5f,1f);
            case "designer":
                return Sound.getSound(SoundEffect.DESIGNER_CRAFT_COMPLETE, SoundCategory.BLOCKS,0.5f,1f);
            case "artist":
                return Sound.getSound(SoundEffect.ARTIST_CRAFT_COMPLETE, SoundCategory.BLOCKS,0.5f,1f);
            case "shaman":
                return Sound.getSound(SoundEffect.SHAMAN_CRAFT_COMPLETE, SoundCategory.BLOCKS,0.5f,1f);
        }
        return null;
    }
}
