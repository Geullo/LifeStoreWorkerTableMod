package com.geullo.workercrafttable.Oaktong;

import com.geullo.workercrafttable.Packet.HandlerPacket;
import com.geullo.workercrafttable.PacketList;
import com.geullo.workercrafttable.PacketMessage;
import com.geullo.workercrafttable.util.SoundEffect;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class TileOaktong extends TileEntity implements ITickable, ISidedInventory {
    public static final int INPUT_SIZE = 5;
    public static final int OUTPUT_SIZE = 3;
    public static final int SIZE = INPUT_SIZE+OUTPUT_SIZE;
    public EntityPlayer playerIn;
    public UUID barkeeper;
    private Random random = new Random();
    public AtomicBoolean success = new AtomicBoolean(false);
    public List<ItemStack> buckkets = new ArrayList<>();

    public static final int MAX_PROGRESS = 200;
    public int progress = 0;
    public int a;
    private ItemStack makeItem;

    @Override
    public void update() {
        if (!world.isRemote) {
            giveAllBuckets();
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            if (progress > 0) {
                List<Integer> slotNum = getBottleIndex();
                slotNum.forEach(i -> {
                    ItemStack result = RecipeOakTong.getInstance().getResult(inputHandler.getStackInSlot(0), inputHandler.getStackInSlot(1), inputHandler.getStackInSlot(i));
                    if (!result.isEmpty()) atomicBoolean.set(true);
                });
                if (slotNum.size() == 0) atomicBoolean.set(false);
                if (atomicBoolean.get()) {
                    progress--;
                    if (progress <= 0) attemptRipening();
                } else {
                    progress = 0;
                }
                markDirty();

            } else {
                startRipening();
            }
        }
    }

    private boolean insertOutput(ItemStack output) {
        for (int i = 0 ; i < OUTPUT_SIZE; i++){
            outputHandler.insertItem(i, output, true);
        }
        return true;
    }

    public void startRipening() {
        List<Integer> slotNum  = getBottleIndex();
        slotNum.forEach(i->{
            ItemStack result = RecipeOakTong.getInstance().getResult(inputHandler.getStackInSlot(0), inputHandler.getStackInSlot(1),inputHandler.getStackInSlot(i));
            if (!result.isEmpty()) {
                if (insertOutput(result.copy())) {
                    progress = MAX_PROGRESS;
                    markDirty();
                }
            }
        });
    }

    private void attemptRipening() {
        List<Integer> slotNum = getBottleIndex();
        success = new AtomicBoolean(false);
        ItemStack[] result2 = {null};
        slotNum.forEach(i -> {
            ItemStack result = RecipeOakTong.getInstance().getResult(inputHandler.getStackInSlot(0), inputHandler.getStackInSlot(1), inputHandler.getStackInSlot(i));
            if (!result.isEmpty()) {
                if (insertOutput(result.copy())) {
                    ItemStack result1 = result.copy();
                    result2[0] = result1;
                    extractBucket(i);
                    markDirty();
                    result1.setCount(1);
                    outputHandler.insertItem(i-2,result1,false);
                    success.set(true);
                    markDirty();
                }
            }
        });
        markDirty();
        if (success.get()) {
            extractBucket(0);
            extractBucket(1);
            world.playSound(null,pos.getX(), pos.getY(), pos.getZ(), SoundEffect.BARKEEPER_CRAFT_COMPLETE, SoundCategory.BLOCKS, 0.74f, 1.0f);
            progress = 0;
            markDirty();
            List<EntityPlayerMP> players = new ArrayList<>(Objects.requireNonNull(world.getMinecraftServer()).getPlayerList().getPlayers());
            makeItem = result2[0].copy();
            for (EntityPlayerMP pl: players) {
                if (pl.getUniqueID().equals(barkeeper)&&makeItem != null) {
                    HandlerPacket.sendTo("오크통 양조가 완료 되었습니다.",makeItem,pl);return;}
            }
            /*players.forEach(bk -> {
                if (bk.getUniqueID().equals(barkeeper)) {
                    if (barkeeper!=null&&bk.getUniqueID().equals(barkeeper)&&makeItem != null) bk.sendMessage(new TextComponentString(("§-§+"+buckkets.size()+"/오크통 양조가 완료 되었습니다.>>"+buckkets.size()+"/"+ Item.getIdFromItem(makeItem.getItem())+":"+ makeItem.getItemDamage()+":"+ makeItem.getCount())));
//                    PacketHandler.INSTANCE.sendTo(new ClientMessage("§-§+"+buckkets.size()+"/오크통 양조가 완료 되었습니다.>>"+buckkets.size()+"/"+ Item.getIdFromItem(makeItem.getItem())+":"+ makeItem.getItemDamage()+":"+ makeItem.getCount()), bk);
                    return;
                }
            });
            if (world.isRemote) send(result2[0].copy());*/
        }
    }
    private void extractBucket(int slot) {
        inputHandler.extractItem(slot, 1, false);
        markDirty();
        if (!world.isRemote&&isBukket(inputHandler,slot)) {
            List<EntityPlayerMP> players = new ArrayList<>(Objects.requireNonNull(world.getMinecraftServer()).getPlayerList().getPlayers());
                AtomicBoolean b = new AtomicBoolean(false);
                players.forEach(bk -> {
                    if (bk.getUniqueID().equals(barkeeper)) {
                        b.set(true);
                        if (!bk.inventory.addItemStackToInventory(new ItemStack(Items.BUCKET))) {
                            EntityItem item = new EntityItem(world, bk.getPosition().getX(), bk.getPosition().getY(), bk.getPosition().getZ(), new ItemStack(Items.BUCKET));
                            item.setNoPickupDelay();
                            world.spawnEntity(item);
                            bk.playSound(SoundEvents.BLOCK_ENDERCHEST_OPEN, 0.85f, 1f);
                            bk.sendMessage(new TextComponentString("§6§l[인생상회§6§l] §f양동이가 인벤토리 안에 들어가지 못해 주변 어딘가에 떨어졌습니다."));
                        }
                        markDirty();
                    }
                });
                if (!b.get()) {
                    buckkets.add(new ItemStack(Items.BUCKET));
                }
        }
    }
    public void giveAllBuckets() {
        if (buckkets.isEmpty()) return;
        List<EntityPlayerMP> players = new ArrayList<>(Objects.requireNonNull(world.getMinecraftServer()).getPlayerList().getPlayers());
        AtomicBoolean b = new AtomicBoolean(false);
        players.forEach(a->{
            if (a.getUniqueID().equals(barkeeper)) {
                b.set(true);
                AtomicBoolean g = new AtomicBoolean(false);
                buckkets.forEach(c -> {
                    if (a != null && !a.inventory.addItemStackToInventory(c)) {
                        EntityItem item = new EntityItem(world, a.getPosition().getX(), a.getPosition().getY(), a.getPosition().getZ(), c);
                        item.setNoPickupDelay();
                        world.spawnEntity(item);
                        a.sendMessage(new TextComponentString("§6§l[인생상회§6§l] §f양동이가 인벤토리 안에 들어가지 못해 주변 어딘가에 떨어졌습니다."));
                    } /*else {
                        a.sendMessage(new TextComponentString("§6§l[인생상회§6§l] §f양동이가 지급되었습니다."));
                    }*/
                });
                if (g.get()) {
                    a.sendMessage(new TextComponentString("§6§l[인생상회§6§l] §f양동이가 인벤토리 안에 들어가지 못했습니다."));
                    a.playSound(SoundEvents.BLOCK_ENDERCHEST_OPEN,0.85f,1f);
                }
                //if (makeItem!=null) PacketHandler.INSTANCE.sendTo(new ClientMessage("§-§+"+buckkets.size()+"/오크통 양조가 완료 되었습니다.>>"+buckkets.size()+"/"+ Item.getIdFromItem(makeItem.getItem())+":"+ makeItem.getItemDamage()+":"+ makeItem.getCount()), (EntityPlayerMP) a);
                buckkets.clear();
                return;
            }
        });
    }

    @SideOnly(Side.CLIENT)
    private void send(ItemStack item) {
        PacketMessage.sendMessage(PacketList.ADD_JOB_POINT.recogCode+"/오크통 양조가 완료 되었습니다./"+ Item.getIdFromItem(item.getItem())+":"+item.getItemDamage()+":"+item.getCount());
    }

    public static boolean isBukket(ItemStackHandler inputHandler,int slot){
        return inputHandler.getStackInSlot(slot).getItem() == Items.WATER_BUCKET || inputHandler.getStackInSlot(slot).getItem() == Items.MILK_BUCKET || inputHandler.getStackInSlot(slot).getItem() == Items.LAVA_BUCKET;
    }

    public List<Integer> getBottleIndex() {
        List<Integer> slotNum = new ArrayList<>();
        for (int i = 2; i < INPUT_SIZE; i++) {
            if (inputHandler.getStackInSlot(i) != ItemStack.EMPTY) {
                slotNum.add(i);
            }
        }
        return slotNum;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        markDirty();
    }

    private final ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SIZE) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if (slot<2) return RecipeOakTong.getInstance().isRipeningIngredient(stack)&&RecipeOakTong.getInstance().isIngredient(stack);
            else return RecipeOakTong.getInstance().isRipeningIngredient(stack)&&RecipeOakTong.getInstance().isBottle(stack);
        }
        @Override
        public int getSlotLimit(int slot) {
            if (slot>1) return 16;
            return 64;
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileOaktong.this.markDirty();
        }
    };

    private final ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SIZE) {
        @Override
        public int getSlotLimit(int slot) {
            return 64;
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return false;
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileOaktong.this.markDirty();
        }
    };

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("itemsIn")){
            inputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
        }
        if (compound.hasKey("itemsOut")){
            outputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsOut"));
        }
        progress = compound.getInteger( "progress");
        barkeeper = compound.getUniqueId("barkeeper");
        loadItemList(compound,buckkets);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("itemsIn",inputHandler.serializeNBT());
        compound.setTag("itemsOut",outputHandler.serializeNBT());
        compound.setInteger("progress",progress);
        if (barkeeper!=null) compound.setUniqueId("barkeeper",barkeeper);
        saveItemList(compound,buckkets);
        return compound;
    }
    public static NBTTagCompound saveItemList(NBTTagCompound tag, List<ItemStack> itemStacks)
    {
        NBTTagList tagList = new NBTTagList();

        for(int i = 0; i < itemStacks.size(); ++i)
        {
            ItemStack itemStack = itemStacks.get(i);

            if(!itemStack.isEmpty())
            {
                NBTTagCompound compound = itemStack.writeToNBT(new NBTTagCompound());
                tagList.appendTag(compound);
            }
        }

        if(!tagList.hasNoTags())
        {
            tag.setTag("Items", tagList);
        }

        return tag;
    }
    public static void loadItemList(NBTTagCompound tag, List<ItemStack> itemStacks)
    {
        NBTTagList tagList = tag.getTagList("Items", 10);

        for(int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound compound = tagList.getCompoundTagAt(i);
            itemStacks.add(new ItemStack(compound));
        }
    }
    private final CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler,outputHandler);

    public boolean canInteractWith(EntityPlayer player){
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
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            if (facing == null) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
            } else  if (facing == EnumFacing.UP){
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);
            } else {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputHandler);
            }
        }
        return super.getCapability(capability, facing);
    }
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
        return combinedHandler.getStackInSlot(index);
    }
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return combinedHandler.extractItem(index,count,false);
    }
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return combinedHandler.extractItem(index,1,false);
    }
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        combinedHandler.setStackInSlot(index,stack);
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
        return combinedHandler.getStackInSlot(index) == stack;
    }
    @Override
    public int getField(int id) {
        return 0;
    }
    @Override
    public void setField(int id, int value) {}
    @Override
    public int getFieldCount() {
        return 0;
    }
    @Override
    public void clear() {}
    @Override
    public String getName() {
        return null;
    }
    @Override
    public boolean hasCustomName() {
        return false;
    }
}
