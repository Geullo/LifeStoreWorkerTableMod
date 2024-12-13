package com.geullo.workercrafttable.Table.GameUI;

import com.geullo.workercrafttable.Table.ContainerTable;
import com.geullo.workercrafttable.Table.Games.DesignGame;
import com.geullo.workercrafttable.Table.TileTable;
import com.geullo.workercrafttable.util.Reference;
import com.geullo.workercrafttable.util.Render;
import com.geullo.workercrafttable.util.Utils;
import com.life.item.item.ItemInit;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class DesignGameUI extends GuiTable {
    private double[] progressPos = new double[2], progressSize = new double[2], sewingMachineBtn = new double[4];

    private DesignGame designGame;
    private boolean frameToggle = false;
    private int mouseX, mouseY;
    private double motion = 1d;
    @Override
    public void denyItemAdd(int level) {
        denyCraftItem.clear();
        designGame.setStorelevel(level);
        switch (level) {
            case 1:
                denyCraftItem.add(ItemInit.red_fabric);
                denyCraftItem.add(ItemInit.blue_fabric);
                denyCraftItem.add(ItemInit.black_leather);
                denyCraftItem.add(ItemInit.white_leather);
            case 2:
                denyCraftItem.add(ItemInit.red_tshirt);
                denyCraftItem.add(ItemInit.blue_tshirt);
                denyCraftItem.add(ItemInit.blue_jeans);
                denyCraftItem.add(ItemInit.leather_jacket);
                denyCraftItem.add(ItemInit.shoes);
                denyCraftItem.add(ItemInit.exercise_shoes);
            case 3:
                denyCraftItem.add(ItemInit.aquamarine_shirt);
                denyCraftItem.add(ItemInit.flower_shoes);
                denyCraftItem.add(ItemInit.wine_dress);
                break;
        }
    }

    public DesignGameUI(ContainerTable inventorySlotsIn, String name, TileTable table) {
        super(inventorySlotsIn, name, table);
        designGame = new DesignGame(1);
        path = "games/designer_game/";
    }

    @Override
    public void initGui() {
        super.initGui();
        bgSize[0] = btnSize[0]*3.5;
        bgSize[1] = btnSize[1]*8.25;
        bgPos[0] = (width/2) - (bgSize[0]/2);
        bgPos[1] = (height/2) - (bgSize[1]/1.2);
        initSewingMachine();
        initProgress();

    }

    private void initSewingMachine() {
        sewingMachineBtn[0] = (bgPos[0]+bgSize[0])-btnSize[0]/5;
        sewingMachineBtn[1] = bgPos[1]+bgSize[1]/2;
        sewingMachineBtn[2] = btnSize[0]*1.25;
        sewingMachineBtn[3] = bgSize[1]/2.15;
    }

    private void initProgress() {
        progressSize[0] = bgSize[0]*2;
        progressSize[1] = btnSize[1]*3.15;
        progressPos[0] = bgPos[0]+(((bgSize[0]+(sewingMachineBtn[2]/2))/2)-(progressSize[0]/2));
        progressPos[1] = bgPos[1]+bgSize[1]+btnSize[0]/5;
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (gameVisible) drawMiniGame(partialTicks,mouseX,mouseY);
        else super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawMiniGame(float partialTicks, int mouseX, int mouseY) {
        super.drawMiniGame(partialTicks,mouseX,mouseY);
        this.mouseX = mouseX;this.mouseY = mouseY;
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,path+"button.png"));
        Render.setColor(0xffffffff);
        if (Utils.between(mouseX,mouseY, sewingMachineBtn[0], sewingMachineBtn[1], sewingMachineBtn[2], sewingMachineBtn[3])) Render.drawTexturedRect(sewingMachineBtn[0], sewingMachineBtn[1], sewingMachineBtn[2], sewingMachineBtn[3],0,0,0.5,1.0);
        else Render.drawTexturedRect(sewingMachineBtn[0], sewingMachineBtn[1], sewingMachineBtn[2], sewingMachineBtn[3],0.5,0,1.0,1.0);
        if (designGame.clicked) {
            if (frameToggle) {
                Render.bindTexture(new ResourceLocation(Reference.MOD_ID, path + "sewing_machine_on_1.png"));
                Render.setColor(0xffffffff);
                Render.drawTexturedRect(bgPos[0], bgPos[1], bgSize[0], bgSize[1]);
            }
            else {
                Render.bindTexture(new ResourceLocation(Reference.MOD_ID, path + "sewing_machine_on_2.png"));
                Render.setColor(0xffffffff);
                Render.drawTexturedRect(bgPos[0], bgPos[1], bgSize[0], bgSize[1]);
            }
        }
        else {
            Render.bindTexture(new ResourceLocation(Reference.MOD_ID,path+"sewing_machine_off.png"));
            Render.setColor(0xffffffff);
            Render.drawTexturedRect(bgPos[0], bgPos[1], bgSize[0], bgSize[1]);
        }


        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,path+"progressbar.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(progressPos[0], progressPos[1], progressSize[0], progressSize[1],0,0,1.0,0.5);
        double maximum = designGame.maximum, cursor = designGame.cursor;
        Render.drawTexturedRect(progressPos[0], progressPos[1], Utils.percent(progressSize[0],Utils.percentPartial(maximum,cursor)), progressSize[1],0,0.5,Utils.percentPartial(maximum,cursor)/100,1.0);
        motion++;
    }

    @Override
    public void updateScreen() {
        if (designGame.clicked) {
            frameToggle = !frameToggle;
            String b = designGame.check();
            if (b.equals("11")) {
                try {
                    successGame();
                } catch (IOException e ) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (designGame.clicked&&!Utils.between(mouseX,mouseY, sewingMachineBtn[0], sewingMachineBtn[1], sewingMachineBtn[2], sewingMachineBtn[3])) {
            designGame.clicked = false;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if (designGame.clicked) designGame.clicked = false;
    }

    @Override
    protected void buttonClick() {
        designGame.cursor = 0;
        super.buttonClick();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (Utils.between(mouseX,mouseY, sewingMachineBtn[0], sewingMachineBtn[1], sewingMachineBtn[2], sewingMachineBtn[3])) {
            designGame.clicked = true;
        }
    }

    @Override
    protected void initGame() {
        super.initGame();
    }
}
