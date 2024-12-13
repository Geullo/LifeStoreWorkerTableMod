//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.geullo.workercrafttable.PotStand;

import com.geullo.workercrafttable.Cauldron.TileCauldron;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerPotStand extends Container {
    private TilePotStand potStand;
    private static final int LOCKED_ID = 0,TIMER = 1;
    public World world;
    public BlockPos pos;
    public EntityPlayer player;

    public ContainerPotStand(EntityPlayer player, IInventory playerInventory, World world, BlockPos pos, TilePotStand te) {
        this.world = world;
        this.pos = pos;
        this.potStand = te;
        this.player = player;
        this.addOwnSlots(playerInventory);
        this.addPlayerSlots(playerInventory);
    }

    private void addPlayerSlots(IInventory playerInventory) {
        int x;
        int r;
        for(r = 0; r < 3; ++r) {
            for(int c = 0; c < 9; ++c) {
                x = 8 + c * 18;
                int y = r * 18 + 67;
                this.addSlotToContainer(new Slot(playerInventory, c + r * 9 + 9, x, y));
            }
        }

        for(r = 0; r < 9; ++r) {
            x = 8 + r * 18;
            int y = 125;
            this.addSlotToContainer(new Slot(playerInventory, r, x, y));
        }

    }

    private void addOwnSlots(IInventory playerInventory) {
        IItemHandler itemHandler = (IItemHandler)this.potStand.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null);
        int x = 54;
        int y = 16;
        int slotIndex = 0;
        this.addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));
        x += 18;
        this.addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));
        x += 18;
        this.addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));
        x += 18;
        this.addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));
        x = 54;
        y = 34;
        this.addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));
        x = x + 18;
        this.addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));
        x += 18;
        this.addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));
        x += 18;
        this.addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x, y));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.potStand.canInteractWith(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < TilePotStand.SIZE) {
                if (!this.mergeItemStack(itemstack1, TilePotStand.SIZE, this.inventorySlots.size(), true)) {return ItemStack.EMPTY;}
            }
            else if (!this.mergeItemStack(itemstack1, 0, TilePotStand.SIZE, false)) {return ItemStack.EMPTY;}

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }
            else slot.onSlotChanged();
        }

        return itemstack;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (IContainerListener listener : listeners) {
            if (potStand.isClosed() != potStand.isClientClosed()) {
                potStand.setClientClosed(potStand.isClosed());
                listener.sendWindowProperty(this, LOCKED_ID, potStand.isClosed() ? 1 : 0);
            }
            if (potStand.getClientTimer()!= potStand.getTimer()) {
                potStand.setClientTimer(potStand.getTimer());
                listener.sendWindowProperty(this, TIMER, potStand.getTimer());
            }
        }
    }
    @Override
    public void updateProgressBar(int id, int data) {
        if (id == LOCKED_ID){
            potStand.setClosed(data==1);
            potStand.setClientClosed(data == 1);
        }
        if (id == TIMER){
            potStand.setTimer(data);
            potStand.setClientTimer(data);
        }
//        if (id==LOCKED_ID) potStand.setClosed(data==1);
//        if (id==TIMER) potStand.setTimer(data);
    }

}
