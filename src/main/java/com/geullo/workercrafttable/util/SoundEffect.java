package com.geullo.workercrafttable.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import net.minecraftforge.registries.IForgeRegistry;


@SuppressWarnings("WeakerAccess")
public class SoundEffect {
    public static SoundEvent FURNITURE_MAKER_CRAFT_COMPLETE;
    public static SoundEvent SHAMAN_CRAFT_COMPLETE;
    public static SoundEvent JEWELER_CRAFT_COMPLETE;
    public static SoundEvent ARTIST_CRAFT_COMPLETE;
    public static SoundEvent CHEF_CRAFT_COMPLETE;
    public static SoundEvent DESIGNER_CRAFT_COMPLETE;
    public static SoundEvent BARKEEPER_CRAFT_COMPLETE;
    public static SoundEvent SUPERMARKET_CRAFT_COMPLETE;
    public static SoundEvent SEND_MONEY;
    public static SoundEvent BROADCAST;
    public static SoundEvent WHISPER;



    public static void registerSounds(IForgeRegistry<SoundEvent> e){
        FURNITURE_MAKER_CRAFT_COMPLETE = registerSound("job.furniture_maker.craft.complete",e);
        SHAMAN_CRAFT_COMPLETE = registerSound("job.shaman.craft.complete",e);
        JEWELER_CRAFT_COMPLETE = registerSound("job.jeweler.craft.complete",e);
        ARTIST_CRAFT_COMPLETE = registerSound("job.artist.craft.complete",e);
        CHEF_CRAFT_COMPLETE = registerSound("job.chef.craft.complete",e);
        DESIGNER_CRAFT_COMPLETE = registerSound("job.designer.craft.complete",e);
        BARKEEPER_CRAFT_COMPLETE = registerSound("job.barkeeper.craft.complete",e);
        SUPERMARKET_CRAFT_COMPLETE = registerSound("job.supermarket.craft.complete",e);
        SEND_MONEY = registerSound("send_money",e);
        BROADCAST = registerSound("broadcast",e);
        WHISPER = registerSound("whisper",e);
    }

    private static SoundEvent registerSound(String soundName, IForgeRegistry e){
        final ResourceLocation soundId = new ResourceLocation(Reference.MOD_ID,soundName);
        SoundEvent soundEvent = new SoundEvent(soundId).setRegistryName(soundName);
        e.register(soundEvent);
        return soundEvent;
    }
}
