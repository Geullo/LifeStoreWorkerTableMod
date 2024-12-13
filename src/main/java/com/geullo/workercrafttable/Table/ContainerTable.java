package com.geullo.workercrafttable.Table;

import com.geullo.workercrafttable.PacketList;
import com.geullo.workercrafttable.PacketMessage;
import com.geullo.workercrafttable.util.Sound;
import com.life.item.item.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContainerTable extends Container {
    public InventoryCrafting crafting = new InventoryCrafting(this,3,3);
    public TileTable table;
    public InventoryCraftResult craftResult = new InventoryCraftResult();
    private List<Item> blackList = new ArrayList<>();
    public World world;
    private BlockPos pos;
    public EntityPlayer player;
    public boolean successed;
    private String name;
    private int cnt = 0,resultCnt = 0;

    public ContainerTable(InventoryPlayer playerInventory, World world, BlockPos pos, TileTable table, String name) {
        this.world = world;
        this.pos = pos;
        this.player = playerInventory.player;
        this.table = table;
        this.name = name;
        blackList.add(ItemInit.noodle);
        blackList.add(ItemInit.bread);
        blackList.add(ItemInit.chocolate);
        blackList.add(ItemInit.plastic_bag);

        addOwnSlots(playerInventory);
        addPlayerSlots(playerInventory);
    }
    private void addPlayerSlots(IInventory playerInventory){
        int x,y;
        for (int r = 0;r<3;++r) {
            for (int c = 0; c < 9; ++c) {
                x = 8 + c * 18;
                y = r * 18 + 84;
                this.addSlotToContainer(new Slot(playerInventory, c + r * 9 + 9, x, y));
            }
        }
        for (int r = 0;r<9;++r) {
            x = 8 + r * 18;y = 142;this.addSlotToContainer(new Slot(playerInventory,r,x,y));
        }
    }

    private void addOwnSlots(InventoryPlayer playerInventory) {
        int x = 120, y=31;
        int a = 4;
        int slotIndex  = 0;
        x += a;
        y += a;
        addSlotToContainer(new SlotCrafting(playerInventory.player,crafting,craftResult,slotIndex,x,y));

        x = 30;
        y = 17;
        for (int i=0;i<9;i++){
            addSlotToContainer(new Slot(crafting,slotIndex++,x,y));
            if (slotIndex!=0&&slotIndex%3==0){
                x=30;
                y+=18;
            }else{
                x += 18;
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getDistanceSq(pos.add(0.5D,0.5D,0.5D)) <= 64D;
    }

    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        this.slotChangedCraftingGrid(this.world, this.player, this.crafting, this.craftResult);
    }

    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (!this.world.isRemote)
        {
            this.clearContainer(playerIn, this.world, this.crafting);
        }
    }

    public void giveItem(ItemStack itemStack) {
        EntityItem item = new EntityItem(world, player.posX, player.posY, player.posZ, itemStack.copy());
        item.setNoPickupDelay();
        world.spawnEntity(item);
    }


    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return slotIn.inventory!=craftResult && super.canMergeSlot(stack, slotIn);
    }
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 0) {
                itemstack1.getItem().onCreated(itemstack1, this.world, playerIn);
                if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemstack1, itemstack);
            } else if (index >= 10 && index < 37) {
                if (!this.mergeItemStack(itemstack1, 37, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 37 && index < 46) {
                if (!this.mergeItemStack(itemstack1, 10, 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            ItemStack itemStack3;
            if (slot.slotNumber==0) {
                if (!world.isRemote) {
                    cnt++;
                    if (cnt == 1) resultCnt = getResultCnt();
                    if (cnt == resultCnt) {
                        if (!world.isRemote && successed) {
                            itemStack3 = itemstack.copy();
                            itemStack3.setCount(resultCnt);
                            giveItem(itemStack3);
                        }
                        cnt = 0;
                        resultCnt = 0;
                    }
                } else {
                    itemStack3 = itemstack1.copy();
                    successed = bigSuccessCraft(itemstack.getItem());
                    Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(table.tableBlock.ga(table.tableBlock.getUnlocalizedName()), SoundCategory.BLOCKS, Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.BLOCKS), 1.2f));
                    if (!blackList.contains(itemStack3.copy().getItem())) {
                        if (successed)
                            send(itemstack.getItem(), getKrName() + "(이)가 조합에 대성공해서 아이템을 2배로 획득했습니다.", itemStack3.copy(), getResultCnt() * 2);
                        else
                            send(itemstack.getItem(), getKrName() + "(이)가 조합에 성공했습니다.", itemStack3.copy(), getResultCnt());
                    }
                }
            }

            itemStack3 = slot.onTake(playerIn, itemstack1);
            if (index == 0) {
                playerIn.dropItem(itemStack3, false);
            }
        }
        return itemstack;
    }
    private boolean bigSuccessCraft(Item item) {
        if (item.getCreativeTab()!=null) {
            return new Random().nextInt(1000000)+1<=2;
        }
        return false;
    }
    private int getResultCnt() {
        int minCnt = 128;
        for (int i = 0; i < 9; i++) {
            if (!crafting.getStackInSlot(i).getItem().equals(Items.AIR)) {
                minCnt = Math.min(minCnt, crafting.getStackInSlot(i).getCount());
            }
        }
        return minCnt==128?0:minCnt;
    }
    @SideOnly(Side.CLIENT)
    private void send(Item item,String successMsg,ItemStack itemStack,int cnt){
        PacketMessage.sendMessage(PacketList.ADD_JOB_POINT.recogCode+"/"+successMsg+"/"+ Item.getIdFromItem(item)+":"+itemStack.getItemDamage()+":"+cnt);
    }
    private String getKrName() {
        switch (name) {
            case "furniture":
                return "목수";
            case "shaman":
                return "무당";
            case "jeweler":
                return "보석상";
            case "artist":
                return "예술가";
            case "designer":
                return "디자이너";
            case "supermarket":
                return "슈퍼";
            default:
                return "";
        }
    }

}
