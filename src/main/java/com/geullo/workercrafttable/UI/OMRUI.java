package com.geullo.workercrafttable.UI;

import com.geullo.bank.util.Utils;
import com.geullo.workercrafttable.Mh;
import com.geullo.workercrafttable.PacketList;
import com.geullo.workercrafttable.PacketMessage;
import com.geullo.workercrafttable.util.Reference;
import com.geullo.workercrafttable.util.Render;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class OMRUI extends GuiScreen {
    private double[] bg = new double[4], btnX = new double[4], btnY= new double[1], btnSz = new double[2];
    private int selected;

    public void setSelected(int selected,boolean sendPacket) {
        this.selected = selected;
        if (sendPacket) {
            PacketMessage.sendMessage(PacketList.SET_LAST_OMR.recogCode + selected);
            Mh.getInstance().lastOmr = selected;
        }
    }

    public int getSelected() {
        return selected;
    }

    public OMRUI(int selected) {
        this.selected = selected;
        PacketMessage.sendMessage(PacketList.GET_LAST_OMR.recogCode);
    }

    @Override
    public void initGui() {
        bg[0] = Render.getWP(638,true);
        bg[1] = Render.getWP(708,false);
        bg[2] = Render.getWP(644,true);
        bg[3] = Render.getWP(224,false);
        btnSz[0] = Render.getWP(95,true);
        btnSz[1] = Render.getWP(146,false);
        btnY[0] = Render.getWP(748,false);
        btnX[0] = Render.getWP(704,true);
        btnX[1] = Render.getWP(840,true);
        btnX[2] = Render.getWP(981,true);
        btnX[3] = Render.getWP(1121,true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"omrcard/bg.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(bg[0],bg[1],bg[2],bg[3]);
        for (int i = 0; i < 4; i++) {
            Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"omrcard/"+(i+1)+".png"));
            Render.setColor(0xffffffff);
            Render.drawTexturedRect(btnX[i],btnY[0],btnSz[0],btnSz[1]);
            if (i == selected) {
                Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"omrcard/check.png"));
                Render.setColor(0xffffffff);
                Render.drawTexturedRect(btnX[i],btnY[0],btnSz[0],btnSz[1]);
            }
            else if (Utils.mouseBetweenIcon(mouseX,mouseY,btnX[i],btnY[0],btnSz[0],btnSz[1])) {
                Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"omrcard/check.png"));
                Render.setColor(0x80717171);
                Render.drawTexturedRect(btnX[i],btnY[0],btnSz[0],btnSz[1]);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (int i = 0; i < 4; i++) {
            if (Utils.mouseBetweenIcon(mouseX,mouseY,btnX[i],btnY[0],btnSz[0],btnSz[1])) {
                if (i!=selected&&mouseButton==0) setSelected(i,true);
                else if (mouseButton==1&&i==selected) setSelected(-1,true);
                return;
            }
        }
    }

}
