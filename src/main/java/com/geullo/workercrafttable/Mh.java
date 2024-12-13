package com.geullo.workercrafttable;

import com.geullo.workercrafttable.Cauldron.GuiCauldron;
import com.geullo.workercrafttable.Events.Events;
import com.geullo.workercrafttable.Events.RenderGameOverlay;
import com.geullo.workercrafttable.Oaktong.GuiOakTong;
import com.geullo.workercrafttable.Table.GameUI.*;
import com.geullo.workercrafttable.UI.OMRUI;
import com.geullo.workercrafttable.proxy.ClientProxy;
import com.geullo.workercrafttable.util.Sound;
import com.geullo.workercrafttable.util.SoundEffect;
import com.life.item.item.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.SoundCategory;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Mh {
	
	private static Mh instance;
	
	public Minecraft mc;
	public static final HashMap<String, Integer> playerScore = new HashMap<>();

	public int lastOmr = -1;
	public static Mh getInstance() {
		if(instance == null) {
			instance = new Mh();
			playerScore.put("양띵",0);
			playerScore.put("루태",0);
			playerScore.put("콩콩",0);
			playerScore.put("눈꽃",0);
			playerScore.put("서넹",0);
			playerScore.put("후추",0);
			playerScore.put("삼식",0);
			playerScore.put("다주",0);
		}
		return instance;
	}
	
	private Mh() {
		mc = Minecraft.getMinecraft();
	}

	public void handleMessage(PacketMessage message) {
		String code = message.data.substring(0, 2);
		if (code.equals(PacketList.GET_STORE_LEVEL.recogCode)) {
			if (mc.currentScreen instanceof DesignGameUI) {
				DesignGameUI ui = (DesignGameUI) mc.currentScreen;
				ui.denyItemAdd(Integer.parseInt(message.data.substring(2)));
			}
			else if (mc.currentScreen instanceof MoksuGameUI) {
				MoksuGameUI ui = (MoksuGameUI) mc.currentScreen;
				ui.denyItemAdd(Integer.parseInt(message.data.substring(2)));
			}
			else if (mc.currentScreen instanceof MusicGameUI) {
				MusicGameUI ui = (MusicGameUI) mc.currentScreen;
				ui.denyItemAdd(Integer.parseInt(message.data.substring(2)));
			}
			else if (mc.currentScreen instanceof SegongGameUI) {
				SegongGameUI ui = (SegongGameUI) mc.currentScreen;
				ui.denyItemAdd(Integer.parseInt(message.data.substring(2)));
			}
			else if (mc.currentScreen instanceof TimingGameUI) {
				TimingGameUI ui = (TimingGameUI) mc.currentScreen;
				ui.denyItemAdd(Integer.parseInt(message.data.substring(2)));
			}
			if (mc.currentScreen instanceof GuiTable) {
				GuiTable ui = (GuiTable) mc.currentScreen;
				ui.denyItemAdd(Integer.parseInt(message.data.substring(2)));
			}
			else if (mc.currentScreen instanceof GuiCauldron) {
				GuiCauldron ui = (GuiCauldron) mc.currentScreen;
				ui.initLvl(Integer.parseInt(message.data.substring(2)));
			}
			else if (mc.currentScreen instanceof GuiOakTong) {
				GuiOakTong ui = (GuiOakTong) mc.currentScreen;
				ui.initLvl(Integer.parseInt(message.data.substring(2)));
			}
		}
		else if (code.equals(PacketList.SEND_MONEY.recogCode)) {
			Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEffect.SEND_MONEY, SoundCategory.PLAYERS,0.45f,1f));
		}
		else if (code.equals(PacketList.TIMER_UPDATE.recogCode)) {
			Events.getInstance().leftTime = message.data.substring(2);
		}
		else if (code.equals(PacketList.GET_LAST_OMR.recogCode)) {
			lastOmr = Integer.parseInt(message.data.substring(2));
			if (mc.currentScreen instanceof OMRUI) {
				OMRUI ui = (OMRUI) mc.currentScreen;
				ui.setSelected(lastOmr,false);
			}
		}
		else if (code.equals(PacketList.QUIZ_VISIBLE.recogCode)) {
			ClientProxy.renderGameOverlay.quizUIVisible = !ClientProxy.renderGameOverlay.quizUIVisible;
        }
		else if (code.equals(PacketList.SCORE_UPDATE.recogCode)) {
			String[] mems = message.data.substring(2).split(",");
			for (int i = 0; i < mems.length; i++) {
				String[] data = mems[i].split(":");
				if (playerScore.containsKey(data[0])) playerScore.put(data[0], Integer.parseInt(data[1]));
			}
		}
		else if (code.equals(PacketList.BROADCAST.recogCode)) {
			ClientProxy.renderGameOverlay.broadcast = message.data.substring(2);
		}
	}

	private void denyItemAdd(List<Item> denyItems,int level) {
		switch (level) {
			case 1:
				denyItems.add(ItemInit.red_fabric);
				denyItems.add(ItemInit.blue_fabric);
				denyItems.add(ItemInit.black_leather);
				denyItems.add(ItemInit.white_leather);
				denyItemAdd(denyItems,2);
				break;
			case 2:
				denyItems.add(ItemInit.red_tshirt);
				denyItems.add(ItemInit.blue_tshirt);
				denyItems.add(ItemInit.blue_jeans);
				denyItems.add(ItemInit.leather_jacket);
				denyItems.add(ItemInit.shoes);
				denyItems.add(ItemInit.exercise_shoes);
				denyItemAdd(denyItems,3);
				break;
		}
	}
}
