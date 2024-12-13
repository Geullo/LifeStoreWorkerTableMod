package com.geullo.workercrafttable.util;

import com.geullo.workercrafttable.util.Potion.PotionBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class PotionInit {
    public static final Potion STAT_UPGRADE_EFFECT = new PotionBase("stat_upgrade" ,false,10578852,0,0);
    public static final PotionType STAT_UPGRADE = new PotionType("stat_upgrade",new PotionEffect(STAT_UPGRADE_EFFECT,2400)).setRegistryName("stat_upgrade");

    public static void registerPotions() {
        registerPotion(STAT_UPGRADE, STAT_UPGRADE_EFFECT);
    }

    private static void registerPotion(PotionType defaultPotion,Potion effect)
    {
        ForgeRegistries.POTIONS.register(effect);
        ForgeRegistries.POTION_TYPES.register(defaultPotion);
    }

}
