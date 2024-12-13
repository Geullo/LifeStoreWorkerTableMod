package com.geullo.workercrafttable.UI;

import com.geullo.bank.Packet;
import com.geullo.bank.util.Utils;
import com.geullo.workercrafttable.PacketList;
import com.geullo.workercrafttable.util.Reference;
import com.geullo.workercrafttable.util.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class CurseDollUI extends GuiScreen {
    private double[] plPos = new double[16], plSz = new double[2],bg = new double[4];
    public CurseDollUI() {
    }

    @Override
    public void initGui() {
        plPos[0] = getWP(405,true);
        plPos[1] = getWP(74,false);

        plPos[2] = getWP(692,true);
        plPos[3] = getWP(74,false);

        plPos[4] = getWP(980,true);
        plPos[5] = getWP(74,false);

        plPos[6] = getWP(1267,true);
        plPos[7] = getWP(74,false);

        plPos[8] = getWP(405,true);
        plPos[9] = getWP(569,false);

        plPos[10] = getWP(692,true);
        plPos[11] = getWP(569,false);

        plPos[12] = getWP(980,true);
        plPos[13] = getWP(569,false);

        plPos[14] = getWP(1267,true);
        plPos[15] = getWP(569,false);
        plSz[0] = getWP(247,true);
        plSz[1] = getWP(437,false);


    }
    private double getWP(double wp,boolean isWX) {
        return (wp/(isWX?1920d:1080d))*(isWX?width:height);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawDefaultBackground();
        for (int i = 0; i < 16;i+=2) {
            Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "doll/" + getPlayereng(i) + "_doll.png"));
            Render.setColor(Utils.mouseBetweenIcon(mouseX, mouseY, plPos[i], plPos[i+1], plSz[0], plSz[1])?0xffffffff:0xff808080);
            Render.drawTexturedRect(plPos[i], plPos[i+1], plSz[0], plSz[1]);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (int i = 0; i < 16;i+=2) {
            if (Utils.mouseBetweenIcon(mouseX, mouseY, plPos[i], plPos[i+1], plSz[0], plSz[1])) {
                Packet.sendMessage(PacketList.CURSE_PLAYER.recogCode+""+getPlayer(i));
                Minecraft.getMinecraft().displayGuiScreen(null);
                return;
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private String getPlayer(int i) {
        switch (i) {
            case 0:
                return "양띵";
            case 2:
                return "루태";
            case 4:
                return "후추";
            case 6:
                return "콩콩";
            case 8:
                return "눈꽃";
            case 10:
                return "삼식";
            case 12:
                return "다주";
            case 14:
                return "서넹";
        }
        return "";
    }
    private String getPlayereng(int i) {
        switch (i) {
            case 0:
                return "didEld";
            case 2:
                return "fnxo";
            case 4:
                return "gncn";
            case 6:
                return "zhdzhd";
            case 8:
                return "snsRhc";
            case 10:
                return "tkatlr";
            case 12:
                return "ekwn";
            case 14:
                return "tjspd";
        }
        return "";
    }
}
