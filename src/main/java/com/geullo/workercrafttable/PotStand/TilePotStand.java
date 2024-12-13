package com.geullo.workercrafttable.PotStand;

import com.geullo.workercrafttable.Block.PotStand;
import com.life.item.item.ItemInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;


public class TilePotStand extends TileEntity implements ITickable,ISidedInventory {
    public static final int SIZE = 8;
    public EntityPlayer playerIn;
    private boolean closed = false,clientClosed = false;
    public static final int TIMER_INIT = 900,TIMER_INITIALI = TIMER_INIT*20;
    private int timer = 0,clientTimer;

    public int getClientTimer() {
        return clientTimer;
    }

    public void setClientTimer(int clientTimer) {
        this.clientTimer = clientTimer;
        markDirty();
    }

    public boolean isClientClosed() {
        return clientClosed;
    }

    public void setClientClosed(boolean clientClosed) {
        this.clientClosed = clientClosed;
        markDirty();
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
        markDirty();
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
        markDirty();
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            if (this.timer<0) {
                this.timer = 0;
                markDirty();
            }
            if (!this.closed) {
                this.timer = 0;
                markDirty();
                return;
            }
            if (this.timer != 0) {
                this.timer--;
                markDirty();
            }
            if (this.timer <=0){
                this.timer = 0;
                markDirty();
                upgradeKimchi();
            }
        }
    }

    private ItemStackHandler itemHandler = new ItemStackHandler(SIZE) {
        @Override
        public int getSlotLimit(int slot) {
            return 32;
        }
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return (stack.getUnlocalizedName().equals("item.old_kimchi")||stack.getUnlocalizedName().equals("item.instance_kimchi")|| stack.getUnlocalizedName().equals("item.sour_kimchi"))&&!closed;
        }
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
            TilePotStand.this.markDirty();
        }
    };

    public void upgradeKimchi() {
        boolean check = !isNextLevel(true)&&!isNextLevel(false)&&!isNextLevel(false);
        if (check||!isNextLevel(false)) {
            setClosed(false);
            PotStand potstand = (PotStand) blockType;
        }
        else {
            setTimer(TIMER_INITIALI);
        }
    }

    private boolean isNextLevel(boolean itemInsert) {
        boolean c = false;
        for (int i = 0; i <SIZE;i++) {
            ItemStack kk = itemHandler.getStackInSlot(i).copy();
            if (KimchiUpgrades.upgrades.containsKey(kk.getItem())) {
                if (itemInsert) {
                    itemHandler.setStackInSlot(i,new ItemStack(KimchiUpgrades.upgrades.get(kk.getItem()),kk.getCount()));
                    markDirty();
                }
                c = true;
            }
        }
        return c;
    }
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("ripening")){
            itemHandler.deserializeNBT((NBTTagCompound) compound.getTag("ripening"));
        }
        if (compound.hasKey("closed")) this.closed = compound.getBoolean("closed");
        if (compound.hasKey("timer")) this.timer = compound.getInteger("timer");

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("ripening", itemHandler.serializeNBT());
        compound.setBoolean("closed",closed);
        compound.setInteger("timer",timer);
        return compound;
    }

    public boolean canInteractWith(EntityPlayer player) {
        return player.getDistanceSq(pos.add(0.5D,0.5D,0.5D)) <= 64D;
    }
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
        return super.hasCapability(capability, facing);
    }



    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
        return super.getCapability(capability, facing);
    }

// ----------------------------------------------------------------------------

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return SIZE;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return itemHandler.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return itemHandler.extractItem(index,count,false);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return itemHandler.extractItem(index,1,false);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        itemHandler.setStackInSlot(index,stack);
        markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return itemHandler.getStackInSlot(index) == stack;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 8;
    }

    @Override
    public void clear() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
    static class KimchiUpgrades {
        public static final HashMap<Item,Item> upgrades = new HashMap<>();
        static {
            upgrades.put(ItemInit.instance_kimchi,ItemInit.old_kimchi);
            upgrades.put(ItemInit.old_kimchi,ItemInit.sour_kimchi);
        }

    }
}
