package com.geullo.workercrafttable.util.Potion;

import com.geullo.workercrafttable.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionBase extends Potion {
    public String name;
    public PotionBase(String name, boolean isBadEffectIn, int liquidColorIn, int iconIdxX, int iconIdxY) {
        super(isBadEffectIn, liquidColorIn);
        setPotionName("effect."+name);
        setIconIndex(iconIdxX,iconIdxY);
        this.name = name;
        setRegistryName(new ResourceLocation(Reference.MOD_ID+":"+name));
    }

    @Override
    public boolean hasStatusIcon() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID ,"potions/"+this.name+".png"));
        return true;
    }

}
