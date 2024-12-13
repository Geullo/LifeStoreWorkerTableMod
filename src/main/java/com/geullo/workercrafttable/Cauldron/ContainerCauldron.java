package com.geullo.workercrafttable.Cauldron;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCauldron extends Container {
    private TileCauldron cauldron;
    private static final int PROGRESS_ID = 0,FUEL_ID=1;
    private World world;
    private BlockPos pos;
    public EntityPlayer player;

    public ContainerCauldron(EntityPlayer player,IInventory playerInventory, World world, BlockPos pos, TileCauldron te) {
        this.world = world;
        this.pos = pos;
        this.cauldron = te;
        this.player = player;
        addOwnSlots();
        addPlayerSlots(playerInventory);
    }

    private void addPlayerSlots(IInventory playerInventory) {
        int x, y;
        for (int r = 0; r < 3; ++r) {
            for (int c = 0; c < 9; ++c) {
                x = 8 + c * 18;
                y = r * 18 + 84;
                this.addSlotToContainer(new Slot(playerInventory, c + r * 9 + 9, x, y));
            }
        }
        for (int r = 0; r < 9; ++r) {
            x = 8 + r * 18;
            y = 142;
            this.addSlotToContainer(new Slot(playerInventory, r, x, y));
        }
    }

    private void addOwnSlots(){
        IItemHandler itemHandler = this.cauldron.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int x = 53, y=6;
        int slotIndex  = 0;
        addSlotToContainer(new SlotItemHandler(itemHandler,slotIndex++,x,y));x +=18;
        addSlotToContainer(new SlotItemHandler(itemHandler,slotIndex++,x,y));x = 53;y=24;
        addSlotToContainer(new SlotItemHandler(itemHandler,slotIndex++,x,y));x +=18;
        addSlotToContainer(new SlotItemHandler(itemHandler,slotIndex++,x,y));x = 62;y=51;
        addSlotToContainer(new SlotItemHandler(itemHandler,slotIndex++,x,y));x = 123;y=16;
        addSlotToContainer(new SlotItemHandler(itemHandler,slotIndex,x,y));
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        cauldron.chef = player.getUniqueID();
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return cauldron.canInteractWith(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < TileCauldron.SIZE) {
                if (!this.mergeItemStack(itemstack1, TileCauldron.SIZE, this.inventorySlots.size(), true)) {return ItemStack.EMPTY;}
            }
            else if (!this.mergeItemStack(itemstack1, 0, TileCauldron.SIZE, false)) {return ItemStack.EMPTY;}
            if (itemstack1.isEmpty()) {slot.putStack(ItemStack.EMPTY);}
            else {slot.onSlotChanged();}
        }
        return itemstack;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (IContainerListener listener : listeners) {
            if (cauldron.getProgress() != cauldron.getClientProgress()) {
                cauldron.setClientProgress(cauldron.getProgress());
                listener.sendWindowProperty(this, PROGRESS_ID, cauldron.getProgress());
            }
            if (cauldron.getFuelProgress() != cauldron.getClientFuelProgress()) {
                cauldron.setClientFuelProgress(cauldron.getFuelProgress());
                listener.sendWindowProperty(this, FUEL_ID, cauldron.getFuelProgress());
            }
        }
    }

    @Override
    public void updateProgressBar(int id, int data) {
        if (id == PROGRESS_ID){
            cauldron.setProgress(data);
            cauldron.setClientProgress(data);
        }
        if (id == FUEL_ID) {
            cauldron.setFuelProgress(data);
            cauldron.setClientFuelProgress(data);
        }
    }
}
