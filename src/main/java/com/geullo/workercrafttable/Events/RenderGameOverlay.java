package com.geullo.workercrafttable.Events;
import com.geullo.workercrafttable.Mh;
import com.geullo.workercrafttable.util.Reference;
import com.geullo.workercrafttable.util.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class RenderGameOverlay extends Gui {
    private double[] bg = new double[4], scoreX = new double[8], scoreY= new double[8], ftSz = new double[2];
    public boolean quizUIVisible = false;
    public String broadcast = "0";
    private List<String> memList = new ArrayList<>();
    Minecraft mc = Minecraft.getMinecraft();
    public RenderGameOverlay() {
        Mh.getInstance();
        memList.add("양띵");
        memList.add("루태");
        memList.add("콩콩");
        memList.add("눈꽃");

        memList.add("서넹");
        memList.add("후추");
        memList.add("삼식");
        memList.add("다주");
    }
    private void initQuiz(ScaledResolution sc) {
        bg[0] = Render.getWP(1640,true);
        bg[1] = Render.getWP(322,false);
        bg[2] = Render.getWP(270,true);
        bg[3] = Render.getWP(437,false);
        ftSz[0] = Render.getWP(40,true);
        ftSz[1] = Render.getWP(40,false);

        scoreX[0] = Render.getWP(1744,true);
        scoreY[0] =  Render.getWP(407,false);

        scoreX[1] = Render.getWP(1744,true);
        scoreY[1] = Render.getWP(451,false);

        scoreX[2] = Render.getWP(1744,true);
        scoreY[2] = Render.getWP(496,false);

        scoreX[3] = Render.getWP(1744,true);
        scoreY[3] = Render.getWP(540,false);

        scoreX[4] = Render.getWP(1744,true);
        scoreY[4] = Render.getWP(584,false);

        scoreX[5] = Render.getWP(1744,true);
        scoreY[5] = Render.getWP(628,false);

        scoreX[6] = Render.getWP(1744,true);
        scoreY[6] = Render.getWP(672,false);

        scoreX[7] = Render.getWP(1744,true);
        scoreY[7] = Render.getWP(716,false);
    }

    @SubscribeEvent
    public void onRedner(RenderGameOverlayEvent e) {
        if(e.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            if (quizUIVisible) {
                initQuiz(e.getResolution());
                Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "omrcard/scoreboard.png"));
                Render.setColor(0xffffffff);
                Render.drawTexturedRect(bg[0], bg[1], bg[2], bg[3]);
                for (int i = 0; i < memList.size(); i++) {
                    Render.drawString(Mh.playerScore.get(memList.get(i)) + "", (float) scoreX[i], (float) scoreY[i], (float) ftSz[0], (float) ftSz[1], 0);
                }
            }
            if (!broadcast.equals("0")) {
                Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "omrcard/broadcast_"+broadcast+".png"));
                Render.setColor(0xffffffff);
                Render.drawTexturedRect(0, 0, e.getResolution().getScaledWidth_double(), Render.getWP(244,false));
            }
        }
    }


}
