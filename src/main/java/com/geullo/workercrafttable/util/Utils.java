package com.geullo.workercrafttable.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;

public class Utils {
    public static int mouseOverColor = 0xffa8a8a8;
    public static boolean between(double mouseX, double mouseY, double x, double y, double width, double height){
        return (mouseX >= x&&mouseY >= y&&mouseX <= x + width&&mouseY <= y + height);
    }
    public static boolean between(int mouseX, int mouseY, int x, int y, int width, int height){
        return (mouseX >= x&&mouseY >= y&&mouseX <= x + width&&mouseY <= y + height);
    }

    public static boolean between(int max,int min,int value){
        return (value>=min&&max>=value);
    }
    public static boolean between(double max,double min,double value){
        return (value>=min&&max>=value);
    }

    public static boolean between(double mouseX, double mouseY, double mouseWidth, double mouseHeight, double x, double y, double width, double height){
        return ((mouseX >= x&&mouseY >= y)||(mouseX + mouseWidth <= x + width&&mouseY + mouseHeight <= y + height));
    }

    public static double percentPartial(double total,double partial){
        return partial/total*100;
    }
    public static int percentPartial(int total,int partial){
        return partial/total*100;
    }

    public static double percent(double total,double percentage){
        return total*percentage/100;
    }
    public static int percent(int total,int percentage){
        return total*percentage/100;
    }
    public static BufferedImage getScreenImg() {
        ScaledResolution sc = new ScaledResolution(Minecraft.getMinecraft());
        int width = sc.getScaledWidth()*sc.getScaleFactor(), height = sc.getScaledHeight()*sc.getScaleFactor();
        FloatBuffer imgBuffer = BufferUtils.createFloatBuffer(width*height*3);
        GL11.glReadPixels(0,0,width,height,GL11.GL_RGB,GL11.GL_FLOAT, imgBuffer);
        imgBuffer.rewind();
        int[] rgbArr = new int[width*height];
        for (int y=0;y<height;++y) {
            for (int x=0;x<width;++x) {
                int r = (int) (imgBuffer.get() * 255) << 16;
                int g = (int) (imgBuffer.get() * 255) << 8;
                int b = (int) (imgBuffer.get() * 255);
                int i = ((height-1) - y) * width + x;
                rgbArr[i] = r+g+b;
            }
        }
        BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        img.setRGB(0,0,width,height,rgbArr,0, width);
        return img;
    }
}
