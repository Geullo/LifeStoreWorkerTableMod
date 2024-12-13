package com.geullo.workercrafttable.Events;

import com.geullo.bank.UI.ATMUI;
import com.geullo.coinchange.CoinType;
import com.geullo.coinchange.UI.CoinChangeUI;
import com.geullo.workercrafttable.Block.utils.BlockRegistry;
import com.geullo.workercrafttable.PacketList;
import com.geullo.workercrafttable.PacketMessage;
import com.geullo.workercrafttable.Table.CraftTableUI;
import com.geullo.workercrafttable.Table.InventoryUI;
import com.geullo.workercrafttable.UI.CurseDollUI;
import com.geullo.workercrafttable.util.PotionInit;
import com.geullo.workercrafttable.util.Reference;
import com.geullo.workercrafttable.util.Render;
import com.geullo.workercrafttable.util.Utils;
import com.life.item.item.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ChatType;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class Events {
    private static Events instance;
    private double[] fsize = new double[1],headPos = new double[4], headSize = new double[4];
    private int[] coordPos , rotatePos , landPos , memPos ;
    public String leftTime = null;
    public boolean timeVisible = false,KEY_PRESSED =false;
    private Minecraft mc = Minecraft.getMinecraft();
    public static Events getInstance(){
        if (instance == null) {
            instance=new Events();
        }
        return instance;
    }
    private KeyBinding forward;
    private KeyBinding backward;
    private KeyBinding left;
    private KeyBinding right;
    public Events(){
        forward = Minecraft.getMinecraft().gameSettings.keyBindForward;
        backward = Minecraft.getMinecraft().gameSettings.keyBindBack;
        left = Minecraft.getMinecraft().gameSettings.keyBindLeft;
        right = Minecraft.getMinecraft().gameSettings.keyBindRight;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void openRecipeButtonUI(PlayerInteractEvent.RightClickBlock e) {
        if (e.getWorld().getBlockState(e.getPos()).getBlock().equals(BlockRegistry.coin_change_machine_1)||e.getWorld().getBlockState(e.getPos()).getBlock().equals(BlockRegistry.fake_block)&&(e.getWorld().getBlockState(e.getPos().add(0,-1,0)).getBlock().equals(BlockRegistry.coin_change_machine_1))) {
            Minecraft.getMinecraft().displayGuiScreen(new CoinChangeUI(CoinType.COIN_TO_JOB_POINT));
        }
        else if (e.getWorld().getBlockState(e.getPos()).getBlock().equals(BlockRegistry.coin_change_machine_2)||e.getWorld().getBlockState(e.getPos()).getBlock().equals(BlockRegistry.fake_block)&&(e.getWorld().getBlockState(e.getPos().add(0,-1,0)).getBlock().equals(BlockRegistry.coin_change_machine_2))) {
            Minecraft.getMinecraft().displayGuiScreen(new CoinChangeUI(CoinType.JOB_POINT_TO_COIN));
        }
        else if (e.getWorld().getBlockState(e.getPos()).getBlock().equals(BlockRegistry.coin_change_machine_3)||e.getWorld().getBlockState(e.getPos()).getBlock().equals(BlockRegistry.fake_block)&&(e.getWorld().getBlockState(e.getPos().add(0,-1,0)).getBlock().equals(BlockRegistry.coin_change_machine_3))) {
            Minecraft.getMinecraft().displayGuiScreen(new CoinChangeUI(CoinType.COIN_TO_STAT_POINT));
        }
        else if (e.getWorld().getBlockState(e.getPos()).getBlock().equals(BlockRegistry.atmBlock)||e.getWorld().getBlockState(e.getPos()).getBlock().equals(BlockRegistry.fake_block)&&(e.getWorld().getBlockState(e.getPos().add(0,-1,0)).getBlock().equals(BlockRegistry.atmBlock))) {
            Minecraft.getMinecraft().displayGuiScreen(new ATMUI(true));
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void openRecipeButtonUI(GuiOpenEvent e) {
        if (e.getGui() instanceof GuiCrafting){
            e.setGui(new CraftTableUI(mc.player.inventory,mc.world,mc.objectMouseOver.getBlockPos()));
        }
        else if (e.getGui() instanceof GuiInventory){
            GuiInventory guiInventory = (GuiInventory) e.getGui();
            e.setGui(new InventoryUI(mc.player));
        }
    }

    public void initUI(List<String> list) {
        ScaledResolution sc = new ScaledResolution(Minecraft.getMinecraft());
        double width = sc.getScaledWidth_double(), height = sc.getScaledHeight_double();
        coordPos = new int[list.size()];
        rotatePos = new int[list.size()];
        landPos = new int[list.size()];
        memPos = new int[list.size()];
        double size = 1.45;
        headSize[0] = (147.12643678160921/1920)*width;
        headSize[1] = (146.47543484894723/1080)*height;
        headPos[0] = (14.712643678160921 /1920)*width;
        headPos[1] = (14.647543484894722 /1080)*height;
        fsize[0] = headSize[0]/3;
        coordPos[0] = (int) headPos[0];
        coordPos[1] = (int) ((headPos[1]+headSize[1])+(headSize[1]/2.55));
        rotatePos[0] = coordPos[0];
        rotatePos[1] = (int) (coordPos[1]+fsize[0]);
        landPos[0] = rotatePos[0];
        landPos[1] = (int) (rotatePos[1]+fsize[0]);
        memPos[0] = landPos[0];
        memPos[1] = (int) (landPos[1]+fsize[0]);
    }
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void b(GuiScreenEvent.KeyboardInputEvent e) {
        if (RegisterKeybind.showLeftTime.isKeyDown()) {
            if (!KEY_PRESSED) {
                KEY_PRESSED = true;
                timeVisible = !timeVisible;
            }
        }
        else if (!RegisterKeybind.showLeftTime.isKeyDown()) KEY_PRESSED = false;
    }
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void b(InputEvent.KeyInputEvent e) {
        if (RegisterKeybind.showLeftTime.isKeyDown()) {
            if (!KEY_PRESSED) {
                KEY_PRESSED = true;
                timeVisible = !timeVisible;
            }
        }
        else if (!RegisterKeybind.showLeftTime.isKeyDown()) KEY_PRESSED = false;
    }
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRender(RenderGameOverlayEvent.Pre e) {
        if (e.getType().equals(RenderGameOverlayEvent.ElementType.DEBUG)) {
            e.setCanceled(true);
            EntityPlayer player = mc.player;
            if (player != null) {
                List<String> view = getDebugList();
                initUI(view);
                Render.drawString("§f"+String.format("x: §c%.1f §fy: §c%.1f §fz: §c%.1f", mc.player.posX, mc.player.posY, mc.player.posZ), (float) coordPos[0], (float) coordPos[1], (float) fsize[0],0);
                Render.drawString("§f"+String.format("방향: §6%s", getDirect(MathHelper.wrapDegrees(mc.player.getPitchYaw().y))), (float) rotatePos[0], (float) rotatePos[1], (float) fsize[0],0);
                Render.drawString("§f"+String.format("지형: §6%s", mc.world.getBiome(mc.player.getPosition()).getBiomeName()), (float) landPos[0], (float) landPos[1], (float) fsize[0],0);
                long i = Runtime.getRuntime().maxMemory();
                long j = Runtime.getRuntime().totalMemory();
                long k = Runtime.getRuntime().freeMemory();
                long l = j - k;
                Render.drawString("§f"+String.format("메모리: %2d%% §7(%03d/%03dMB)",l * 100L / i, bytesToMb(l), bytesToMb(i)), (float) memPos[0], (float) memPos[1], (float) fsize[0],0);
            }
        }
    }
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRender(RenderGameOverlayEvent.Post e) {
        if (e.getType().equals(RenderGameOverlayEvent.ElementType.TEXT)) {
            if (timeVisible) {
                String time = leftTime == null || leftTime.equals("") ? "00:00:00" : leftTime;
                Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "uitexture/timer_bg.png"));
                Render.setColor(0xffffffff);
                double w = e.getResolution().getScaledWidth_double(), h = e.getResolution().getScaledHeight_double();
                Render.drawTexturedRect(0, (192 / 1080d) * h, (386 / 1920d) * w, (98 / 1080d) * h);
                String[] sp = time.split("");
                double[] a = new double[10];
                a[0] = (140 / 1920d) * w;
                a[1] = (220 / 1080d) * h;
                a[2] = (26 / 1920d) * w;
                a[3] = (43 / 1080d) * h;
                a[4] = (8 / 1920d) * w;
                a[5] = (30 / 1080d) * h;
                a[6] = (227.5 / 1080d) * h;
                for (int i = 0; i < sp.length; i++) {
                    Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "uitexture/nums/" + (sp[i].equals(":") ? "sep" : sp[i]) + ".png"));
                    if (
                            ((i == 0) && sp[i].equals("0"))
                            ||(i == 3 && sp[i].equals("0") && sp[i - 2].equals("0") && sp[i - 3].equals("0"))
                            ||(i == 6 && sp[i].equals("0") && sp[i - 2].equals("0") && sp[i - 3].equals("0") && sp[i - 5].equals("0") && sp[i - 6].equals("0"))
                            ||((i == 1) && sp[i].equals("0") && sp[i - 1].equals("0"))
                            ||(i == 4 && sp[i].equals("0") && sp[i - 1].equals("0") && sp[i - 3].equals("0") && sp[i - 4].equals("0"))
                            ||(i == 7 && sp[i].equals("0") && sp[i - 1].equals("0") && sp[i - 3].equals("0") && sp[i - 4].equals("0") && sp[i - 6].equals("0") && sp[i - 7].equals("0"))
                            ||((i == 2) && sp[i].equals(":") && sp[i - 1].equals("0") && sp[i - 2].equals("0"))
                            ||((i == 5) && sp[i].equals(":") && sp[i - 1].equals("0") && sp[i - 2].equals("0") && sp[i - 4].equals("0") && sp[i - 5].equals("0"))
                    ) Render.setColor(0xffb6703b);
                    else Render.setColor(0xffffffff);
                    Render.drawTexturedRect(a[0], a[1], a[2], a[3]);
                    a[0] = (((((a[0] + a[2]) / w) * 1920) + 6) / 1920d) * w;
                    a[3] = sp.length > i + 1 && sp[i + 1].equals(":") ? a[5] : (43 / 1080d) * h;
                    a[2] = sp.length > i + 1 && sp[i + 1].equals(":") ? a[4] : (26 / 1920d) * w;
                    a[1] = sp.length > i + 1 && sp[i + 1].equals(":") ? a[6] : (220 / 1080d) * h;
                }
            }
        }
    }

    public List<String> getDebugList() {
        List<String> debugs = new ArrayList<>();
        debugs.add("§f"+String.format("x: §e§l%.1f §fy: §e§l%.1f §fz: §e§l%.1f", mc.player.posX, mc.player.posY, mc.player.posZ));
        debugs.add("§f"+String.format("방향: §6§l%s", getDirect(MathHelper.wrapDegrees(mc.player.getPitchYaw().y))));
        debugs.add("§f"+String.format("지형: §6§l%s", mc.world.getBiome(mc.player.getPosition()).getBiomeName()));
        long i = Runtime.getRuntime().maxMemory();
        long j = Runtime.getRuntime().totalMemory();
        long k = Runtime.getRuntime().freeMemory();
        long l = j - k;
        debugs.add("§f"+String.format("메모리: §l%2d%% §7(%03d/%03dMB)",l * 100L / i, bytesToMb(l), bytesToMb(i)));

        return debugs;
    }
    private long bytesToMb(long bytes)
    {
        return bytes / 1024L / 1024L;
    }

    public String getDirect(float yaw) {
        return Utils.between(22.4,-22.5,yaw)?"남 ":
               Utils.between(67.4,22.5,yaw)?"남서":
               Utils.between(112.4,67.5,yaw)?"서 ":
               Utils.between(157.4,112.5,yaw)?"북서":
               Utils.between(180,157.5,yaw)?"북 ":
               Utils.between(-157.4,-180,yaw)?"북 ":
               Utils.between(-112.4,-157.5,yaw)?"북동":
               Utils.between(-67.4,-112.5,yaw)?"동 ":
               Utils.between(-22.4,-67.5,yaw)?"남동":
               Utils.between(0,-22.5,yaw)?"남 ": "NULL";
    }
    String lastMsg = "";
    int line;
    int amt;
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void B(PlayerInteractEvent.RightClickItem e) {
        if (!e.getItemStack().getItem().equals(ItemInit.curse_doll)) return;
        Minecraft.getMinecraft().displayGuiScreen(new CurseDollUI());
        e.setCanceled(true);
    }
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void B(ClientChatReceivedEvent e) {
        GuiNewChat guiNewChat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
        if (e.getType().equals(ChatType.CHAT)||e.getType().equals(ChatType.SYSTEM)) {
            if (e.getMessage().getUnformattedText().contains("§-§+")) {
                String[] a = e.getMessage().getUnformattedText().split("/");
                String[] b = a[2].split(":");
                for (int i=0;i<(Integer.parseInt(a[0].replace("§-§+",""))==0?1:Integer.parseInt(a[0].replace("§-§+","")));i++) PacketMessage.sendMessage(PacketList.ADD_JOB_POINT.recogCode+"/"+a[1]+"/"+ b[0] +":"+ b[1]+":"+ b[2]);
                e.setCanceled(true);
                return;
            }
            if (lastMsg.equals(e.getMessage().getFormattedText())) {
                guiNewChat.deleteChatLine(line);
                amt++;
                lastMsg = e.getMessage().getFormattedText();
                e.getMessage().appendText("§7( §cx" + amt + " §7)");
            } else {
                amt = 1;
                lastMsg = e.getMessage().getFormattedText();
            }
            line++;
            if (!e.isCanceled()) guiNewChat.printChatMessageWithOptionalDeletion(e.getMessage(), line);
            e.setCanceled(true);
        }
    }

    /**
     * mc forge Invert Player Manipulation Key
     * */


}
