package com.geullo.workercrafttable.Table.GameUI;

import com.geullo.workercrafttable.Table.ContainerTable;
import com.geullo.workercrafttable.Table.Games.MusicGame;
import com.geullo.workercrafttable.Table.TileTable;
import com.geullo.workercrafttable.util.Reference;
import com.geullo.workercrafttable.util.Render;
import com.geullo.workercrafttable.util.Sound;
import com.geullo.workercrafttable.util.Utils;
import com.life.item.item.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

import java.io.IOException;

public class MusicGameUI extends GuiTable {
    private double[]
            notePosX = new double[8],notePosY = new double[8],noteSize = new double[2],
            saveNotePosY = new double[8],
            whitePianoPosX = new double[8], whitePianoPosY = new double[8], whitePianoSize = new double[2],
            blackPianoPosX = new double[8], blackPianoPosY = new double[8], blackPianoSize = new double[2]
            ;
    private MusicGame musicGame;
    private int noteLength = 3;

    @Override
    public void denyItemAdd(int level) {
        denyCraftItem.clear();
        musicGame.setStorelevel(level);
        switch (level) {
            case 1:
                denyCraftItem.add(ItemInit.cd1);
                denyCraftItem.add(ItemInit.cd2);
                denyCraftItem.add(ItemInit.cd3);
                denyCraftItem.add(ItemInit.cd4);
                denyCraftItem.add(ItemInit.picture);
            case 2:
                denyCraftItem.add(ItemInit.story_book);
                denyCraftItem.add(ItemInit.album1);
                denyCraftItem.add(ItemInit.album2);
                denyCraftItem.add(ItemInit.album3);
                denyCraftItem.add(ItemInit.album4);
            case 3:
                denyCraftItem.add(ItemInit.red_apron);
                denyCraftItem.add(ItemInit.special_sticker);
                denyCraftItem.add(ItemInit.music_box);
                break;
        }
    }

    public MusicGameUI(ContainerTable inventorySlotsIn, String name, TileTable table) {
        super(inventorySlotsIn,name,table);
        musicGame = new MusicGame(1);
        path = "games/music_game/";
    }

    @Override
    public void initGui() {
        super.initGui();
        bgSize[0] = width/2.5d;
        bgSize[1] = height/2.56d;
        bgPos[0] = ((width)/2) - (bgSize[0]/2);
        bgPos[1] = ((height)/2) - (bgSize[1]*1.2);
        initNote();
        initWhitePiano();
    }

    private void initNote() {
        noteSize[0] = bgSize[0]/4.25d/2.15;
        noteSize[1] = bgSize[1]/2.25;
        notePosX[0] = bgPos[0]+(noteSize[0]*2.6);
        initSaveNotes();
        initNotes();
    }

    private void initSaveNotes() {
        saveNotePosY[0] = bgPos[1] + noteSize[1]*1.15;
        saveNotePosY[1] = saveNotePosY[0] - (noteSize[1]/4.3/3);
        saveNotePosY[2] = saveNotePosY[1] - (noteSize[1]/4.3/1.9);
        saveNotePosY[3] = saveNotePosY[2] - (noteSize[1]/4.3/1.352);
        saveNotePosY[4] = saveNotePosY[3] - (noteSize[1]/4.3/1.64);
        saveNotePosY[5] = saveNotePosY[4] - (noteSize[1]/4.3/1.3);
        saveNotePosY[6] = saveNotePosY[5] - (noteSize[1]/4.3/1.6);
        saveNotePosY[7] = saveNotePosY[6] - (noteSize[1]/4.3/1.36);
    }
    private void initNotes() {
        for (int i = 0; i < musicGame.getNotes().size(); i++) {
            if (i!=0) notePosX[i] = (notePosX[i-1]+noteSize[0])+(noteSize[0]/3);
            notePosY[i] = saveNotePosY[MusicGame.Note.indexOf(musicGame.getNotes().get(i))];
        }
    }
    private void initWhitePiano()
    {
        whitePianoSize[0] = bgSize[0]/3.5/2;
        whitePianoSize[1] = bgSize[1]/1.05;
        whitePianoPosX[0] = bgPos[0]-(bgSize[0]/4.25/3.15);
        whitePianoPosY[0] = (bgPos[1]+bgSize[1])+(bgSize[1]/3.85/2.25);
        for (int i=1;i<whitePianoPosX.length;i++) {
            whitePianoPosX[i] = whitePianoPosX[i-1]+whitePianoSize[0]+0.000001;
            whitePianoPosY[i] = whitePianoPosY[0];
        }
        initBlackPiano();
    }
    private void initBlackPiano()
    {
        blackPianoSize[0] = whitePianoSize[0]/1.85;
        blackPianoSize[1] = whitePianoSize[1]/1.5;
        blackPianoPosX[0] = (whitePianoPosX[0]+whitePianoSize[0])-(blackPianoSize[0]/2);
        blackPianoPosY[0] = whitePianoPosY[0];
        blackPianoPosX[1] = whitePianoPosX[1]+(whitePianoSize[0]-(blackPianoSize[0]/2));

        blackPianoPosX[2] = whitePianoPosX[3]+(whitePianoSize[0]-(blackPianoSize[0]/2));

        blackPianoPosX[3] = whitePianoPosX[4]+(whitePianoSize[0]-(blackPianoSize[0]/2));
        blackPianoPosX[4] = whitePianoPosX[5]+(whitePianoSize[0]-(blackPianoSize[0]/2));

        for (int i=1;i<5;i++) {
            blackPianoPosY[i] = blackPianoPosY[0];
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (gameVisible) drawMiniGame(partialTicks,mouseX,mouseY);
        else super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawMiniGame(float partialTicks,int mouseX,int mouseY) {
        super.drawMiniGame(partialTicks,mouseX,mouseY);
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,path+"sheet_music.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(bgPos[0], bgPos[1], bgSize[0], bgSize[1]);

        for (int i=0;i<musicGame.getNotes().size();i++) {
            Render.bindTexture(new ResourceLocation(Reference.MOD_ID, path + "note.png"));
            Render.setColor(musicGame.getNotes().get(i).color);
            if (musicGame.getNotes().get(i).name.equals("SI")||musicGame.getNotes().get(i).name.equals("DO_UP")) {
                Render.drawTexturedRect(notePosX[i], notePosY[i]+(noteSize[1]/1.32), noteSize[0], noteSize[1],Utils.percentPartial(638d,434d)/100,0,1.0d,1.0d);
                if (musicGame.getNotes().get(i).finished) {
                    Render.setColor(0xcc000000);
                    Render.drawTexturedRect(notePosX[i], notePosY[i]+(noteSize[1]/1.32), noteSize[0], noteSize[1],Utils.percentPartial(638d,434d)/100,0,1.0d,1.0d);
                }
            }
            else if (musicGame.getNotes().get(i).name.equals("DO")) {
                Render.drawTexturedRect(notePosX[i], notePosY[i], noteSize[0], noteSize[1],0,0,Utils.percentPartial(638d,230d)/100,1.0d);
                if (musicGame.getNotes().get(i).finished) {
                    Render.setColor(0xcc000000);
                    Render.drawTexturedRect(notePosX[i], notePosY[i], noteSize[0], noteSize[1],0,0,Utils.percentPartial(638d,230d)/100,1.0d);
                }
            }
            else {
                Render.drawTexturedRect(notePosX[i], notePosY[i], noteSize[0], noteSize[1],Utils.percentPartial(638d,230d)/100,0,Utils.percentPartial(638d,434d)/100,1.0d);
                if (musicGame.getNotes().get(i).finished) {
                    Render.setColor(0xcc000000);
                    Render.drawTexturedRect(notePosX[i], notePosY[i], noteSize[0], noteSize[1],Utils.percentPartial(638d,230d)/100,0,Utils.percentPartial(638d,434d)/100,1.0d);
                }
            }
        }

        drawPiano("white",8,mouseX,mouseY,whitePianoPosX,whitePianoPosY,whitePianoSize,0,0,0.5,1.0);
        drawPiano("black",5,mouseX,mouseY,blackPianoPosX,blackPianoPosY,blackPianoSize,0.5,0,1.0,1.0);
    }

    private void drawPiano(String img,int length,int mouseX,int mouseY,double[] x,double[] y,double[] size,double u1,double v1,double u2, double v2) {
        for (int i = 0; i <length;i++) {
            Render.bindTexture(new ResourceLocation(Reference.MOD_ID, path + "piano.png"));
            if (Utils.between(mouseX,mouseY,x[i],y[i],size[0],size[1])&&img.equals("white")&& !isInBlackPiano(mouseX,mouseY)) Render.setColor(Utils.mouseOverColor);
            else Render.setColor(0xffffffff);
            Render.drawTexturedRect(x[i], y[i], size[0], size[1],u1,v1,u2,v2);
        }
    }

    @Override
    protected void buttonClick() {
        super.buttonClick();
        musicGame.init();
        initSaveNotes();
        initNotes();
    }

    private boolean isInBlackPiano(int mouseX, int mouseY) {
        return Utils.between(mouseX, mouseY, blackPianoPosX[0], blackPianoPosY[0], blackPianoSize[0], blackPianoSize[1]) ||
                Utils.between(mouseX, mouseY, blackPianoPosX[1], blackPianoPosY[1], blackPianoSize[0], blackPianoSize[1]) ||
                Utils.between(mouseX, mouseY, blackPianoPosX[2], blackPianoPosY[2], blackPianoSize[0], blackPianoSize[1]) ||
                Utils.between(mouseX, mouseY, blackPianoPosX[3], blackPianoPosY[3], blackPianoSize[0], blackPianoSize[1]) ||
                Utils.between(mouseX, mouseY, blackPianoPosX[4], blackPianoPosY[4], blackPianoSize[0], blackPianoSize[1])  ;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (gameVisible) {
            for (int i = 0; i < MusicGame.Note.values.size(); i++) {
                if (Utils.between(mouseX, mouseY, whitePianoPosX[i], whitePianoPosY[i], whitePianoSize[0], whitePianoSize[1]) && !isInBlackPiano(mouseX,mouseY))
                {
                    String match = musicGame.check(MusicGame.Note.values.get(i));
                    Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvent.REGISTRY.getObject(new ResourceLocation("block.note.harp")), SoundCategory.BLOCKS,100f, MusicGame.Note.values.get(i).pitch));
                    if (match.equals("01")) {
                    } else if (match.equals("11")) successGame();
                    else failedGame();
                    return;
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void initGame() {
        musicGame.getNotes().forEach(n->n.finished = false);
        initNotes();
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
