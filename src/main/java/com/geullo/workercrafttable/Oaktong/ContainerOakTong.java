package com.geullo.workercrafttable.Oaktong;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerOakTong extends Container{
    private TileOaktong oaktong;
    private static final int PROGRESS_ID = 0;
    public World world;
    public BlockPos pos;
    public EntityPlayer player;

    public ContainerOakTong(EntityPlayer player,IInventory playerInventory,World world,BlockPos pos, TileOaktong te) {
        this.world = world;
        this.pos = pos;
        this.oaktong = te;
        this.player = player;
        addOwnSlots();
        addPlayerSlots(playerInventory);
    }

    private void addPlayerSlots(IInventory playerInventory) {
        int x,y;
        for (int r = 0;r<3;++r){
            for (int c = 0;c<9;++c){
                x = 8+c*18;
                y = r * 18 + 84;
                this.addSlotToContainer(new Slot(playerInventory,c+r*9+9,x,y));
            }
        }
        for (int r = 0;r<9;++r){
            x = 8 + r * 18;
            y = 142;
            this.addSlotToContainer(new Slot(playerInventory,r,x,y));
        }
    }

    private void addOwnSlots(){
        IItemHandler itemHandler = this.oaktong.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int x = 23, y=33;
        int slotIndex  = 0;
        addSlotToContainer(new SlotItemHandler(itemHandler,slotIndex++,x,y));x +=20;
        addSlotToContainer(new SlotItemHandler(itemHandler,slotIndex++,x,y));x = 99;y=25;
        addSlotToContainer(new SlotItemHandler(itemHandler,slotIndex++,x,y));x +=20;
        addSlotToContainer(new SlotItemHandler(itemHandler,slotIndex++,x,y));x +=20;
        addSlotToContainer(new SlotItemHandler(itemHandler,slotIndex++,x,y));x = 99;y=47;
        addSlotToContainer(new SlotItemHandler(itemHandler,slotIndex++,x,y));x +=20;
        addSlotToContainer(new SlotItemHandler(itemHandler,slotIndex++,x,y));x +=20;
        addSlotToContainer(new SlotItemHandler(itemHandler,slotIndex,x,y));

    }
    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        oaktong.barkeeper = player.getUniqueID();
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return oaktong.canInteractWith(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < TileOaktong.SIZE) {
                if (!this.mergeItemStack(itemstack1, TileOaktong.SIZE, this.inventorySlots.size(), true)) {return ItemStack.EMPTY;}
            }
            else if (!this.mergeItemStack(itemstack1, 0, TileOaktong.SIZE, false)) {return ItemStack.EMPTY;}
            if (itemstack1.isEmpty()) {slot.putStack(ItemStack.EMPTY);}
            else {slot.onSlotChanged();}
        }

        return itemstack;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (IContainerListener listener : listeners) listener.sendWindowProperty(this,PROGRESS_ID,oaktong.getProgress());
    }

    @Override
    public void updateProgressBar(int id, int data) {
        if (id == PROGRESS_ID) oaktong.setProgress(data);
    }
}
