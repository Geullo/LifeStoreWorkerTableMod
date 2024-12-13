package com.geullo.workercrafttable.Item;

import com.geullo.workercrafttable.util.Reference;
import com.geullo.workercrafttable.util.Render;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class UI extends GuiScreen {
    private double a,b,c,d;
    public UI() {

    }

    @Override
    public void initGui() {
        super.initGui();
        a = (360/1920d)*width;
        b = (140/1080d)*height;
        c = (1200/1920d)*width;
        d = (800/1080d)*height;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"uitexture/warning.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(a,b,c,d);
    }
}
