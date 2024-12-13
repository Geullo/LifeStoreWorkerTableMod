package com.geullo.workercrafttable.Table.GameUI;

import com.geullo.workercrafttable.Table.ContainerTable;
import com.geullo.workercrafttable.Table.Games.SegongGame;
import com.geullo.workercrafttable.Table.TileTable;
import com.geullo.workercrafttable.util.Reference;
import com.geullo.workercrafttable.util.Render;
import com.geullo.workercrafttable.util.Utils;
import com.life.item.item.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SegongGameUI extends GuiTable{
    private final double imgWidth = 932d, imgHeight = 895d;
    private double[]
            breakTexturePosX = new double[4],breakTexturePosY = new double[4], breakTextureSizeW = new double[4],breakTextureSizeH = new double[4],
            gameBtnPosX = new double[4] , gameBtnPosY = new double[4],
            saveGameBtnPosX = new double[4] , saveGameBtnPosY = new double[4],
            gameBtnSize = new double[2];
    private SegongGame segongGame;

    @Override
    public void denyItemAdd(int level) {
        denyCraftItem.clear();
        if (segongGame==null) segongGame = new SegongGame(level);
        else segongGame.setStorelevel(level);
        switch (level) {
            case 1:
                denyCraftItem.add(ItemInit.diamond_necklace);
                denyCraftItem.add(ItemInit.diamond_ring);
                denyCraftItem.add(ItemInit.topaz_necklace);
                denyCraftItem.add(ItemInit.topaz_ring);
            case 2:
                denyCraftItem.add(ItemInit.rose_golden_necklace);
                denyCraftItem.add(ItemInit.rose_golden_ring);
                denyCraftItem.add(ItemInit.aqua_marin_necklace);
                denyCraftItem.add(ItemInit.aqua_marin_ring);
                denyCraftItem.add(ItemInit.ruby_necklace);
                denyCraftItem.add(ItemInit.ruby_ring);
            case 3:
                denyCraftItem.add(ItemInit.rose_necklace);
                denyCraftItem.add(ItemInit.lemon_ring);
                denyCraftItem.add(ItemInit.chung_necklace);
                break;
        }
    }

    public SegongGameUI(ContainerTable inventorySlotsIn, String name, TileTable table) {
        super(inventorySlotsIn, name, table);
        denyItemAdd(1);
        path = "games/segong_game/";
    }

    @Override
    public void initGui() {
        super.initGui();
        bgSize[0] = btnSize[0]*4.5;
        bgSize[1] = btnSize[1]*13.5;
        bgPos[0] = (width/2) - (bgSize[0]/2);
        bgPos[1] = (height/2) - (bgSize[1]/2);
        initGameBtn();
        initBreakTexture();
    }

    private void initBreakTexture() {
        breakTextureSizeW[0] = bgSize[0]/4.730964467;
        breakTextureSizeH[0] = bgSize[1]/4.73544974;
        breakTexturePosX[0] = bgPos[0]+((bgSize[0]/2) - (breakTextureSizeW[0]/2));
        breakTexturePosY[0] = bgPos[1];

        breakTextureSizeW[1] = breakTextureSizeW[0];
        breakTextureSizeH[1] = breakTextureSizeH[0];
        breakTexturePosX[1] = bgPos[0];
        breakTexturePosY[1] = bgPos[1]+((bgSize[1]/2) - (breakTextureSizeH[1]/1.85));

        breakTextureSizeW[2] = breakTextureSizeW[0];
        breakTextureSizeH[2] = breakTextureSizeH[0]*1.2;
        breakTexturePosX[2] = breakTexturePosX[0];
        breakTexturePosY[2] = (bgPos[1]+bgSize[1]) - (breakTextureSizeH[0]);

        breakTextureSizeW[3] = breakTextureSizeW[0];
        breakTextureSizeH[3] = breakTextureSizeH[0];
        breakTexturePosX[3] = bgPos[0]+(bgSize[0]-breakTextureSizeW[3]);
        breakTexturePosY[3] = bgPos[1]+((bgSize[1]/2) - (breakTextureSizeH[3]/2));
    }

    private void initGameBtn() {
        gameBtnSize[0] = btnSize[0]/2.25;
        gameBtnSize[1] = btnSize[1]*1.3;
        initSaveGameBtns();
        initGameBtns();
    }

    private void initSaveGameBtns() {
        saveGameBtnPosX[0] = bgPos[0]+((bgSize[0]/2)-(gameBtnSize[0]/2));
        saveGameBtnPosY[0] = bgPos[1]+(bgSize[1]/4/1.55);

        saveGameBtnPosX[1] = bgPos[0]+(bgSize[0]/4/1.5);
        saveGameBtnPosY[1] = bgPos[1]+(bgSize[1]/2.45);

        saveGameBtnPosX[2] = saveGameBtnPosX[0];
        saveGameBtnPosY[2] = bgPos[1]+(bgSize[1]/1.55);

        saveGameBtnPosX[3] = bgPos[0]+(bgSize[0]/1.35);
        saveGameBtnPosY[3] = saveGameBtnPosY[1];
    }
    private void initGameBtns() {
        int n;
        List<Integer> a = new ArrayList<>();
        for (int i=0;i<segongGame.getSequences().size();i++) {
            n = new Random().nextInt(4);
            if (!a.contains(n)) {
                a.add(n);
                segongGame.getSequences().get(i).setPosId(n);
                gameBtnPosX[i] = saveGameBtnPosX[n];
                gameBtnPosY[i] = saveGameBtnPosY[n];
            }
            else {
                if (i!=0) i--;
            }
        }
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (gameVisible) drawMiniGame(partialTicks, mouseX, mouseY);
        else super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawMiniGame(float partialTicks, int mouseX, int mouseY) {
        super.drawMiniGame(partialTicks,mouseX,mouseY);
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,path+"finish_necklace.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(bgPos[0], bgPos[1], bgSize[0], bgSize[1]);
        int scale = new ScaledResolution(mc).getScaleFactor();
        List<SegongGame.SegongSequence> sequences = segongGame.getSequences();
        for (int i=0;i<sequences.size();i++) {
            if (!sequences.get(i).getFinished()) {
                Render.bindTexture(new ResourceLocation(Reference.MOD_ID, path + "make_necklace.png"));
                Render.setColor(0xffffffff);
                GL11.glPushMatrix();
                GL11.glEnable(GL11.GL_SCISSOR_TEST);
                int k = segongGame.getSequences().get(i).getPosId();
                GL11.glScissor((int) ((breakTexturePosX[k]) * scale),
                        (int) (Minecraft.getMinecraft().displayHeight - (breakTexturePosY[k] + breakTextureSizeW[k]) * scale),
                        (int) (breakTextureSizeW[k] * scale),
                        (int) (breakTextureSizeH[k] * scale));
                Render.drawTexturedRect(bgPos[0], bgPos[1], bgSize[0], bgSize[1]);
                GL11.glDisable(GL11.GL_SCISSOR_TEST);
                GL11.glPopMatrix();
            }
        }
        for (int i=0;i<sequences.size();i++) {
            if (!sequences.get(i).getFinished()) {
                Render.bindTexture(new ResourceLocation(Reference.MOD_ID, path + sequences.get(i).getId() + ".png"));
                if (Utils.between(mouseX, mouseY, gameBtnPosX[i], gameBtnPosY[i], gameBtnSize[0], gameBtnSize[1])) Render.setColor(0xff979797);
                else Render.setColor(0xffffffff);
                Render.drawTexturedRect(gameBtnPosX[i], gameBtnPosY[i], gameBtnSize[0], gameBtnSize[1]);
            }
        }
    }

    @Override
    protected void buttonClick() {
        super.buttonClick();
        segongGame.init();
        initSaveGameBtns();
        initGameBtns();
    }

    boolean a = false;
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar,keyCode);
        if (gameVisible) {denyItemAdd(!a?1:4);a=!a;}
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (gameVisible) {
            for (int i=0;i<segongGame.getSequences().size();i++) {
                if (!segongGame.getSequences().get(i).getFinished() && Utils.between(mouseX, mouseY, gameBtnPosX[i], gameBtnPosY[i], gameBtnSize[0], gameBtnSize[1])) {
                    String match = segongGame.check(i);
                    if (match.equals("01")) {
                    } else if (match.equals("11")) {
                        successGame();
                    } else {
                        failedGame();
                    }
                    return;
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void initGame() {
        segongGame.getSequences().forEach(s->s.setFinished(false));
        initGameBtns();
        super.initGame();
    }

    @Override
    protected void successGame() throws IOException {
        super.successGame();
    }

    @Override
    protected void failedGame() throws IOException {
        initGame();
        super.failedGame();
    }
}
