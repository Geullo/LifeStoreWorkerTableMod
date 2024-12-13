package com.geullo.workercrafttable.Item;

import com.geullo.workercrafttable.PacketList;
import com.geullo.workercrafttable.PacketMessage;
import net.minecraft.client.gui.GuiScreen;

public class CardUI extends GuiScreen {
    public String name;


    public CardUI(String name) {
        PacketMessage.sendMessage(PacketList.GET_BUSINESS_INFO.recogCode);
        this.name = name;
    }
    private double getWP(double wp,boolean isWX) {
        return (wp/(isWX?1920d:1080d))*(isWX?width:height);
    }


}
