package com.geullo.workercrafttable.Table.GameUI;

import com.geullo.workercrafttable.Table.ContainerTable;
import com.geullo.workercrafttable.Table.Games.DesignGame;
import com.geullo.workercrafttable.Table.Games.SuperGame;
import com.geullo.workercrafttable.Table.TileTable;
import com.geullo.workercrafttable.util.Reference;
import com.geullo.workercrafttable.util.Render;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class SuperGameUI extends GuiTable{
    private SuperGame superGame;
    private int level;
    private double[] bg = new double[4],iconSz = new double[2],iconPosX = new double[5],iconPosY = new double[5];

    @Override
    public void denyItemAdd(int level) {
        superGame.setStorelevel(level);
        this.level = level;
        super.denyItemAdd(level);
    }

    public SuperGameUI(ContainerTable inventorySlotsIn, String name, TileTable table) {
        super(inventorySlotsIn, name, table);
        superGame = new SuperGame(1);
        denyItemAdd(4);
        path = "games/super_game/";
    }

    @Override
    public void initGui() {
        super.initGui();
        bg[0] = getWP(438,true);
        bg[1] = getWP(134,false);
        bg[2] = getWP(1019,true);
        bg[3] = getWP(763,false);
        iconSz[0] = getWP(258,true);
        iconSz[1] = getWP(288,false);

        iconPosX[0] = getWP(1086,true);
        iconPosY[0] = getWP(525,false);

        iconPosX[1] = getWP(702,true);
        iconPosY[1] = getWP(525,false);

        iconPosX[2] = getWP(424,true);
        iconPosY[2] = getWP(525,false);

    }

    @Override
    protected void drawMiniGame(float partialTicks, int mouseX, int mouseY) {
        super.drawMiniGame(partialTicks, mouseX, mouseY);
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,path+"background.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(bg[0],bg[1],bg[2],bg[3]);

        for (int i=0;i<superGame.getKeys().size();i++) {
            if (i>2) break;
            Render.bindTexture(new ResourceLocation(Reference.MOD_ID,path+superGame.getKeys().get(i).co+".png"));
            Render.setColor(0xffffffff);
            Render.drawTexturedRect(iconPosX[i],iconPosY[i],iconSz[0],iconSz[1]);
        }
    }
    private double getWP(double wp,boolean isWX) {
        return (wp/(isWX?1920d:1080d))*(isWX?width:height);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if (gameVisible) {
            if (keyCode == superGame.getNowKeyCode().key) {
                if (superGame.next()) {
                    successGame();
                }
            } else {
                failedGame();
            }
        }
    }

    @Override
    protected void initGame() {
        super.initGame();
        superGame.init();
        superGame = new SuperGame(level);
    }
}
