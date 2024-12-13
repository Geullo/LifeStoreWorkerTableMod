package com.geullo.workercrafttable.Table.GameUI;

import com.geullo.workercrafttable.Table.ContainerTable;
import com.geullo.workercrafttable.Table.Games.MoksuGame;
import com.geullo.workercrafttable.Table.TileTable;
import com.geullo.workercrafttable.util.Reference;
import com.geullo.workercrafttable.util.Render;
import com.geullo.workercrafttable.util.Utils;
import com.life.item.item.ItemInit;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class MoksuGameUI extends GuiTable{

    private double[] progressPos = new double[2], progressSize = new double[2];
    private int hammerDown = 0;
    private boolean hammerClicked = false;
    private MoksuGame moksuGame;

    @Override
    public void denyItemAdd(int level) {
        denyCraftItem.clear();
        moksuGame.setStorelevel(level);
        switch (level) {
            case 1:
                denyCraftItem.add(ItemInit.acacia_bird);
                denyCraftItem.add(ItemInit.spruce_clock);
                denyCraftItem.add(ItemInit.birch_fan);
                denyCraftItem.add(ItemInit.jungle_rocking_chair);
            case 2:
                denyCraftItem.add(ItemInit.oak_food_table);
                denyCraftItem.add(ItemInit.darkoak_jagaejang);
                denyCraftItem.add(ItemInit.wonang_pair);
                denyCraftItem.add(ItemInit.cuckoo_clock);
                denyCraftItem.add(ItemInit.birch_folding_screen);
                denyCraftItem.add(ItemInit.jungle_swing);
            case 3:
                denyCraftItem.add(ItemInit.ruby_jagaejang);
                denyCraftItem.add(ItemInit.leather_partion);
                denyCraftItem.add(ItemInit.wine_table);
                break;
        }
    }

    public MoksuGameUI(ContainerTable inventorySlotsIn, String name, TileTable table) {
        super(inventorySlotsIn, name, table);
        moksuGame = new MoksuGame(1);
        path = "games/moksu_game/";
    }

    @Override
    public void initGui() {
        super.initGui();
        bgSize[0] = btnSize[0]*2.25;
        bgSize[1] = btnSize[1]*7.2;
        bgPos[0] = (width/2) - (bgSize[0]/2);
        bgPos[1] = (height/2) - (bgSize[1]/1.5);
        initProgress();
    }

    private void initProgress() {
        progressSize[0] = bgSize[0]*2;
        progressSize[1] = btnSize[1]*1.6;
        progressPos[0] = bgPos[0]+((bgSize[0]/2)-(progressSize[0]/2));
        progressPos[1] = bgPos[1]+bgSize[1];
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (gameVisible) drawMiniGame(partialTicks, mouseX, mouseY);
        else super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawMiniGame(float partialTicks, int mouseX, int mouseY) {
        super.drawMiniGame(partialTicks,mouseX,mouseY);
        if (!hammerClicked) {
            Render.bindTexture(new ResourceLocation(Reference.MOD_ID,path+"before.png"));
            if (Utils.between(mouseX,mouseY,bgPos[0],bgPos[1],bgSize[0],bgSize[1])) Render.setColor(Utils.mouseOverColor);
            else Render.setColor(0xffffffff);
        }
        else Render.bindTexture(new ResourceLocation(Reference.MOD_ID,path+"after.png"));
        Render.drawTexturedRect(bgPos[0], bgPos[1], bgSize[0], bgSize[1]);

        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,path+"progressbar.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(progressPos[0], progressPos[1], progressSize[0], progressSize[1],0,0,1.0,0.5);
        double maximum = moksuGame.maximum, cursor = moksuGame.cursor;
        Render.drawTexturedRect(progressPos[0], progressPos[1], Utils.percent(progressSize[0],Utils.percentPartial(maximum,cursor)), progressSize[1],0,0.5,Utils.percentPartial(maximum,cursor)/100,1.0);
    }

    @Override
    public void updateScreen() {
        if (hammerClicked) {
            hammerDown++;
            if (hammerDown%3 == 0) {
                hammerClicked = false;
                hammerDown = 0;
            }
        }
    }

    @Override
    protected void buttonClick() {
        super.buttonClick();
        moksuGame.init();

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (gameVisible) {
            if (Utils.between(mouseX,mouseY,bgPos[0],bgPos[1],bgSize[0],bgSize[1])&&!hammerClicked) {
                hammerClicked = true;
                String match = moksuGame.check();
                if (match.equals("11")) {
                    successGame();
                }
                return;
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void initGame() {
        super.initGame();
    }

    @Override
    protected void successGame() throws IOException {
        super.successGame();
    }
}
