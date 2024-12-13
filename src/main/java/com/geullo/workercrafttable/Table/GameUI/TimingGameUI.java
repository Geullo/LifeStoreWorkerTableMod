package com.geullo.workercrafttable.Table.GameUI;

import com.geullo.workercrafttable.Table.ContainerTable;
import com.geullo.workercrafttable.Table.TileTable;
import com.geullo.workercrafttable.Table.Games.TimingGame;
import com.geullo.workercrafttable.util.Render;
import com.geullo.workercrafttable.util.Reference;
import com.geullo.workercrafttable.util.Utils;
import com.life.item.item.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimingGameUI extends GuiTable {
    private double[] loadingbarBgSize = new double[2],loadingbarBgPos = new double[2], loadingbarCursorSize = new double[2],loadingbarCursorPos = new double[3];
    private TimingGame timingGame;
    private double arrange = 10;
    private double cursor = 0,b;
    private int lvl = 0;
    private double a = 16.5, c = 10.5;
    private boolean cursorMoved=false,cursorEnded;
    private ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void denyItemAdd(int level) {
        denyCraftItem.clear();
        this.lvl = level;
        timingGame = new TimingGame(getArrange(lvl));
        switch (level) {
            case 1:
                denyCraftItem.add(ItemInit.lavender_sac);
                denyCraftItem.add(ItemInit.daisy_sac);
                denyCraftItem.add(ItemInit.rose_sac);
                denyCraftItem.add(ItemInit.beads);
                denyCraftItem.add(ItemInit.favor_charm);
            case 2:
                denyCraftItem.add(ItemInit.luxury_lavender_sac);
                denyCraftItem.add(ItemInit.luxury_daisy_sac);
                denyCraftItem.add(ItemInit.shaman_bubble);
                denyCraftItem.add(ItemInit.manneung_charm);
            case 3:
                denyCraftItem.add(ItemInit.dream_catcher);
                denyCraftItem.add(ItemInit.mubok);
                denyCraftItem.add(ItemInit.lemon_diffuser);
                break;
        }
    }
    private double getArrange(int lvl) {
        return lvl>2?a:c;
    }

    private boolean isIn(double a,double b,double c,double d) {
        d +=c;
        b +=a;
        return (c>=a&&c<=b&&d>=a&&d<=b)||(a>=c&&a<=d&&b>=c&&b<=d);
    }
    public TimingGameUI(ContainerTable inventorySlotsIn, String name, TileTable table)
    {
        super(inventorySlotsIn,name,table);
        lvl=0;
        timingGame = new TimingGame(getArrange(lvl));
    }

    @Override
    public void initGui() {
        super.initGui();
        if (buttonDrawed) {
            bgSize[0] = width/1.85;
            bgSize[1] = height/4.2;
            bgPos[0] = (width / 2d) - (bgSize[0] / 2);
            bgPos[1] = (height / 2d) - (bgSize[1] / 2);
            loadingbarBgSize[0] = bgSize[0] / 1.12;
            loadingbarBgSize[1] = bgSize[1] / 4;
            loadingbarBgPos[0] = bgPos[0] + ((bgSize[0] / 2) - (loadingbarBgSize[0] / 2));
            loadingbarBgPos[1] = bgPos[1] + ((bgSize[1] / 2) - (loadingbarBgSize[1] / 2));
            loadingbarCursorSize[0] = Utils.percent(loadingbarBgSize[0], 1.5);
            loadingbarCursorSize[1] = loadingbarBgSize[1];
            loadingbarCursorPos[0] = loadingbarBgPos[0];
            loadingbarCursorPos[1] = loadingbarBgPos[1];
            loadingbarCursorPos[2] = loadingbarCursorPos[0];
        }

    }


    @Override
    protected void drawMiniGame(float partialTicks,int mouseX,int mouseY) {
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"games/timing_game/bg.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(bgPos[0], bgPos[1], bgSize[0], bgSize[1]);
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"games/timing_game/loading_bg.png"));
        Render.setColor(0x80ffffff);
        Render.drawTexturedRect(loadingbarBgPos[0], loadingbarBgPos[1], loadingbarBgSize[0], loadingbarBgSize[1]);
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"uitexture/loadingbar_bg.png"));
        Render.setColor(0x807afc65);
        Render.drawTexturedRect(loadingbarBgPos[0]+timingGame.getMinScope(loadingbarBgSize[0]), loadingbarBgPos[1], timingGame.getArrange(loadingbarBgSize[0]), loadingbarBgSize[1]);
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"uitexture/loadingbar_bg.png"));
        Render.setColor(0xffC4302B);
        Render.drawTexturedRect(loadingbarCursorPos[0], loadingbarCursorPos[1], loadingbarCursorSize[0], loadingbarCursorSize[1]);

    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    private double cursorMovePos(double cursorSpeed){
        if (cursor-cursorSpeed<0) cursorEnded = false;
        else if (cursor+cursorSpeed>100) cursorEnded = true;
        if (cursorEnded) return -cursorSpeed;
        else return +cursorSpeed;
    }
    @Override
    protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
        super.handleMouseClick(slotIn, slotId, mouseButton, type);
    }

    @Override
    protected void keyType(int keyCode) throws IOException {
        if (gameVisible&&(keyCode != Keyboard.KEY_ESCAPE&&keyCode!=Keyboard.KEY_E)) {
            cursorMoved=false;
            if (isIn(loadingbarBgPos[0] + timingGame.getMinScope(loadingbarBgSize[0]),timingGame.getArrange(loadingbarBgSize[0])+Utils.percent(loadingbarBgSize[0],Utils.percentPartial(loadingbarBgSize[0],10)),loadingbarCursorPos[0],loadingbarCursorSize[0])) {
                successGame();
            }
            else {
                failedGame();
            }
            initGame();
            btnClicked = false;
            cursor = 0;
            gameVisible = false;
            ex.shutdown();
            return;
        }
        if (gameVisible&&keyCode==Keyboard.KEY_ESCAPE) {
            cursor = 0;
            ex.shutdown();
        }
    }

    @Override
    protected void buttonClick() {
        super.buttonClick();
        timingGame = new TimingGame(getArrange(lvl));
        cursor = 0;
        cursorMoved = true;
        ex = Executors.newSingleThreadScheduledExecutor();
        ex.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (gameVisible&&btnClicked) {
                    if (cursorMoved) {
                        /*if (!cursorMoved) {
                            try {
                                *//*if (isIn(loadingbarBgPos[0] + timingGame.getMinScope(loadingbarBgSize[0]),timingGame.getArrange(loadingbarBgSize[0])+Utils.percent(loadingbarBgSize[0],Utils.percentPartial(loadingbarBgSize[0],10)),loadingbarCursorPos[0],loadingbarCursorSize[0])) {
                                    successGame();
                                }
                                else {
                                    failedGame();
                                }
                                cursor = 0;
                                ex.shutdown();*//*
                            } catch (IOException e) {e.printStackTrace();}
                            return;
                        }*/
                        cursor += cursorMovePos(0.8);
                        loadingbarCursorPos[0] = loadingbarCursorPos[2] + Utils.percent(loadingbarBgSize[0]-loadingbarCursorSize[0],cursor);
                    }
                }
            }
        },0,lvl<3?5:7, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void successGame() throws IOException {
        cursorMoved = false;
        gameVisible = false;
        btnClicked = true;
        buttonEnabled = false;
        super.successGame();
    }

    @Override
    protected void failedGame() throws IOException {
        super.failedGame();
        initGame();
    }


    @Override
    public void onGuiClosed() {
        ex.shutdown();
        super.onGuiClosed();
    }

    @Override
    protected void initGame() {
        timingGame = new TimingGame(getArrange(lvl));
        cursor = 0;
        cursorMoved = false;
        ex.shutdown();
        super.initGame();
    }
}
