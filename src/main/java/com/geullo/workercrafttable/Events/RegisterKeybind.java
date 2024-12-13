package com.geullo.workercrafttable.Events;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class RegisterKeybind {
    public static final String KeyCategory = "§f인생상회";
    public static KeyBinding showLeftTime = new KeyBinding("§f시간 확인", Keyboard.KEY_TAB,KeyCategory);
}
