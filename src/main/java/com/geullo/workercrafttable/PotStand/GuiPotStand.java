package com.geullo.workercrafttable.PotStand;

import com.geullo.workercrafttable.Packet.HandlerPacket;
import com.geullo.workercrafttable.util.Reference;
import com.geullo.workercrafttable.util.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.HashMap;

public class GuiPotStand extends GuiContainer {
    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;
    private String name;
    private HashMap<String,Pos> pos = new HashMap<String, Pos>();
    private static ResourceLocation bg;
    public TilePotStand potStand;
    public ContainerPotStand containerPotStand;
    public GuiRipeningClosedButton closeBtn, openBtn;
    private int[] coords = new int[6];
    public GuiPotStand(ContainerPotStand inventorySlotsIn, TilePotStand potStand, String name)
    {
        super(inventorySlotsIn);
        this.potStand = potStand;
        this.containerPotStand = inventorySlotsIn;
        potStand.playerIn = inventorySlotsIn.player;
        xSize = WIDTH;
        ySize = HEIGHT;
        this.name = name;
        pos.put("0",new Pos(242,3));
        pos.put("1",new Pos(179,3));
        pos.put("2",new Pos(186,3));
        pos.put("3",new Pos(193,3));
        pos.put("4",new Pos(200,3));
        pos.put("5",new Pos(207,3));
        pos.put("6",new Pos(214,3));
        pos.put("7",new Pos(221,3));
        pos.put("8",new Pos(228,3));
        pos.put("9",new Pos(235,3));
        pos.put(":" ,new Pos(249,3));
        bg = new ResourceLocation(Reference.MOD_ID,"uitexture/pot_stand.png");
    }

    @Override
    public void initGui() {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        openBtn = new GuiRipeningClosedButton(0,this.guiLeft+133,this.guiTop+29,30,25,180,29,29,bg);
        closeBtn = new GuiRipeningClosedButton(1,this.guiLeft+142,this.guiTop+29,21,25,189,55,29,bg);
        changeState();
        buttonList.add(openBtn);
        buttonList.add(closeBtn);
        coords[0] = this.guiLeft+50;
        coords[1] = this.guiTop+13;
        coords[2] = 178;
        coords[3] = 81;
        coords[4] = 78;
        coords[5] = 40;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, 54 + 2, 4210752);

    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();
        mc.getTextureManager().bindTexture(bg);
        Render.setColor(0xffffffff);
        drawTexturedModalRect(guiLeft,guiTop,0,0,xSize,ySize);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (openBtn.id == button.id) {
            potStand.setClosed(true);
            // 문닫힘 여부;;x;;y;;z
//            HandlerPacket.INSTANCE.sendToAll(new ServerMessage(true,potStand.getPos()));
            HandlerPacket.sendToServer(true,potStand.getPos());
        }
        else if (closeBtn.id == button.id) {
            potStand.setClosed(false);
//            HandlerPacket.INSTANCE.sendToAll(new ServerMessage(false,potStand.getPos()));
            HandlerPacket.sendToServer(false,potStand.getPos());
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        changeState();
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        Render.bindTexture(bg);
        GlStateManager.disableDepth();
        RenderHelper.enableGUIStandardItemLighting();
        Render.setColor(0xffffffff);
        drawTexturedModalRect(guiLeft+133,guiTop+12,180, 12,33,13);
        String m = String.format("%02d",(Integer.parseInt(String.valueOf(potStand.getTimer()/20/60))));
        m = m+":"+String.format("%02d",Integer.parseInt(String.valueOf(potStand.getTimer()/20%60)));
        int a = 137;
        Render.setColor(0xffffffff);
        for (int i = 0; i < m.length();i++) {
            drawTexturedModalRect(guiLeft+a,guiTop+15,pos.get(""+m.charAt(i)).u, pos.get(""+m.charAt(i)).v,4,7);
            a+=i==2?3:6;
        }
//        if (potStand.isClosed()) {
//            Render.setColor(0xffffffff);
//            drawTexturedModalRect(guiLeft + 50, guiTop + 13, 178, 81, 78, 40);
//        }
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableDepth();
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
        if (slotId<8&&potStand.isClosed()) return;
        super.handleMouseClick(slotIn, slotId, mouseButton, type);
    }

    public void changeState() {
        if (potStand.isClosed()) {
            openBtn.visible = false;
            closeBtn.visible = true;
//            potStand.setTimer(TilePotStand.TIMER_INITIALI);
        }
        else {
//            potStand.setTimer(0);
            closeBtn.visible = false;
            openBtn.visible = true;
        }
    }

    public class GuiRipeningClosedButton extends GuiButtonImage {
        private ResourceLocation dfImg;
        private int u,v;

        public GuiRipeningClosedButton(int id, int x, int y, int width, int height, int u, int v, int tx, ResourceLocation dfImg) {
            super(id, x, y, width, height, u, v, tx, dfImg);
            this.dfImg = dfImg;
            this.u = u;
            this.v = v;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
            if (!this.visible) return;
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            mc.getTextureManager().bindTexture(this.dfImg);
            GlStateManager.disableDepth();
            if (!this.hovered) {
                Render.setColor(0xffffffff);
            }
            else
            {
                Render.setColor(0xff613405);
            }
            this.drawTexturedModalRect(this.x, this.y, u, v, this.width, this.height);
            GlStateManager.enableDepth();
        }
    }

    public class Pos {
        private int u;
        private int v;
        public Pos(int u,int v) {
            this.u = u;
            this.v = v;
        }
    }
}
