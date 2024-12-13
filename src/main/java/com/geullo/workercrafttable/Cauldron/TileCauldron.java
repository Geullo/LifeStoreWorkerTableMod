package com.geullo.workercrafttable.Cauldron;

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


public class TileCauldron extends TileEntity implements ITickable, ISidedInventory {
    public static final int INPUT_SIZE = 5;
    public static final int OUTPUT_SIZE = 1;
    public static final int SIZE = INPUT_SIZE +  OUTPUT_SIZE;
    public UUID chef;
    private Random random = new Random();
    public List<ItemStack> buckkets = new ArrayList<>();

    public static final int MAX_PROGRESS = 180;
    public int level;
    private int fuelProgress = 0;
    private int clientFuelProgress = -1;
    private int progress = 0;
    private int clientProgress = -1;
    public EntityPlayer player;
    private ItemStack makeItem;
    private boolean alalal = false;
    public static final int STEP_ONE_FUEL_PROGRESS = 18;
    public static final int MAX_FUEL_PROGRESS = STEP_ONE_FUEL_PROGRESS*2;
    public TileCauldron() {

    }

    @Override
    public void update() {
        if (!world.isRemote) {
            if (progress > 0) {
                giveAllBuckets();
                ItemStack result = getResult();
                if (!result.isEmpty() && fuelProgress > 0) {
                    progress--;
                    if (progress % 58 == 0 && !world.isRemote)
                        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEffect.CHEF_CRAFT_COMPLETE, SoundCategory.BLOCKS, 0.67f, 1.0f);
                    if (progress == 0) {
                        startCook();
                    }
                    markDirty();
                }
                else {
                    if (!world.isRemote) progress = 0;
                    markDirty();
                }
            } else {
                attemptCook();
            }
        }
    }

    private boolean insertOutput(ItemStack output) {
        for (int i = 0 ; i < OUTPUT_SIZE; i++){
            ItemStack result = outputHandler.insertItem(i, output, true);
            if (result.isEmpty()) return true;
        }
        return false;
    }

    public void attemptCook() {
        ItemStack result = getResult();
        if (!result.isEmpty()) {
            if (insertOutput(result.copy())) {
                if (fuelProgress>0) {
                    progress = MAX_PROGRESS;
                    markDirty();
                }
            }
        }
    }

    public void startCook() {
        ItemStack result = getResult();
        if (!result.isEmpty()) {
            if (insertOutput(result.copy())) {
                if (fuelProgress>0) {
                    outputHandler.insertItem(0, result.copy(), false);
                    markDirty();
                    extractBucket(0);
                    extractBucket(1);
                    extractBucket(2);
                    extractBucket(3);
                    ItemStack itemStack = inputHandler.getStackInSlot(4);
                    alalal = false;
                    if (fuelProgress - 1 >= 0 && !itemStack.copy().isEmpty()
                            && fuelProgress + RecipeCauldron.getInstance().getFuelProgress(itemStack) - 1 <= (itemStack.getItem().equals(Items.COAL) ? STEP_ONE_FUEL_PROGRESS : MAX_FUEL_PROGRESS)) {
                        alalal = true;
                        fuelProgress += RecipeCauldron.getInstance().getFuelProgress(itemStack.copy());
                        markDirty();
                        inputHandler.extractItem(4, 1, false);
                        markDirty();
                    }
                    makeItem = outputHandler.getStackInSlot(0);
                    progress = 0;
                    fuelProgress = fuelProgress - 1 < 1 ? 0 : fuelProgress - 1;
                    markDirty();
                    List<EntityPlayerMP> players = new ArrayList<>(Objects.requireNonNull(world.getMinecraftServer()).getPlayerList().getPlayers());
                    for (EntityPlayerMP pl: players) {
                        if (pl.getUniqueID().equals(chef)&&makeItem != null) {HandlerPacket.sendTo("음식이 완료 되었습니다.",makeItem,pl);return;}
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void send(ItemStack item){
        PacketMessage.sendMessage(PacketList.ADD_JOB_POINT.recogCode+"/음식이 완료 되었습니다./"+ Item.getIdFromItem(item.getItem())+":"+ item.getItemDamage()+":"+ item.getCount());
    }

    private void extractBucket(int slot) {
        inputHandler.extractItem(slot, 1, false);
        markDirty();
        if (!world.isRemote&&isBukket(inputHandler,slot)) {
            List<EntityPlayerMP> players = new ArrayList<>(Objects.requireNonNull(world.getMinecraftServer()).getPlayerList().getPlayers());
                AtomicBoolean b = new AtomicBoolean(false);
                players.forEach(a -> {
                    if (a.getUniqueID().equals(chef)) {
                        b.set(true);
                        if (!a.inventory.addItemStackToInventory(new ItemStack(Items.BUCKET))) {
                            EntityItem item = new EntityItem(world, a.getPosition().getX(), a.getPosition().getY(), a.getPosition().getZ(), new ItemStack(Items.BUCKET));
                            item.setNoPickupDelay();
                            world.spawnEntity(item);
                            a.playSound(SoundEvents.BLOCK_ENDERCHEST_OPEN, 0.85f, 1f);
                            a.sendMessage(new TextComponentString("§6§l[인생상회§6§l] §f양동이가 인벤토리 안에 들어가지 못했습니다."));
                        } /*else a.sendMessage(new TextComponentString("§6§l[인생상회§6§l] §f양동이가 지급되었습니다."));*/
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
        List<EntityPlayer> players = new ArrayList<>(world.playerEntities);
        AtomicBoolean b = new AtomicBoolean(false);
        players.forEach(a->{
            if (a.getUniqueID().equals(chef)) {
                b.set(true);
                AtomicBoolean g = new AtomicBoolean(false);
                buckkets.forEach(c -> {
                    if (a != null && !a.inventory.addItemStackToInventory(c)) {
                        EntityItem item = new EntityItem(world, a.getPosition().getX(), a.getPosition().getY(), a.getPosition().getZ(), c);
                        item.setNoPickupDelay();
                        world.spawnEntity(item);
                        g.set(true);
                    }/* else {
                        a.sendMessage(new TextComponentString("§6§l[인생상회§6§l] §f양동이가 지급되었습니다."));
                    }*/
                });
                if (g.get()) {
                    a.sendMessage(new TextComponentString("§6§l[인생상회§6§l] §f양동이가 인벤토리 안에 들어가지 못했습니다."));
                    a.playSound(SoundEvents.BLOCK_ENDERCHEST_OPEN,0.85f,1f);
                }
                if (makeItem!=null) a.sendMessage(new TextComponentString("§-§+"+buckkets.size()+"/음식이 완료 되었습니다.>>"+buckkets.size()+"/"+ Item.getIdFromItem(makeItem.getItem())+":"+ makeItem.getItemDamage()+":"+ makeItem.getCount()));
                buckkets.clear();
                return;
            }
        });
    }
    public static boolean isBukket(ItemStackHandler inputHandler,int slot){
        return inputHandler.getStackInSlot(slot).getItem() == Items.WATER_BUCKET || inputHandler.getStackInSlot(slot).getItem() == Items.MILK_BUCKET || inputHandler.getStackInSlot(slot).getItem() == Items.LAVA_BUCKET;
    }

    private ItemStack getResult(){
        return RecipeCauldron.getInstance().getResult(inputHandler.getStackInSlot(0), inputHandler.getStackInSlot(1), inputHandler.getStackInSlot(2), inputHandler.getStackInSlot(3));
    }
    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        markDirty();
    }

    public int getFuelProgress() {
        return fuelProgress;
    }
    public void setFuelProgress(int fuelProgress) {
        this.fuelProgress = fuelProgress;
        markDirty();

    }

    public int getClientFuelProgress() {
        return clientFuelProgress;
    }
    public void setClientFuelProgress(int clientFuelProgress) {
        this.clientFuelProgress = clientFuelProgress;
        markDirty();
    }

    public int getClientProgress() {
        return clientProgress;
    }

    public void setClientProgress(int clientProgress) {
        this.clientProgress = clientProgress;
        markDirty();
    }

    ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SIZE) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if (slot == 4) return RecipeCauldron.getInstance().isFuel(stack);
            return RecipeCauldron.getInstance().isCookingIngredient(stack)&&RecipeCauldron.getInstance().isIngredient(stack);
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileCauldron.this.markDirty();
            ItemStack s = getStackInSlot(slot);
            if (slot==4&&s!=null&&!s.copy().isEmpty()
                    &&fuelProgress+RecipeCauldron.getInstance().getFuelProgress(s)-1<= (s.getItem().equals(Items.COAL)?STEP_ONE_FUEL_PROGRESS:MAX_FUEL_PROGRESS)
                    &&!alalal) {
                fuelProgress += RecipeCauldron.getInstance().getFuelProgress(getStackInSlot(slot));
                markDirty();
                extractItem(4,1,false);
                markDirty();
            }
            TileCauldron.this.markDirty();
        }
    };
    ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SIZE) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return false;
        }
        @Override
        protected void onContentsChanged(int slot) {
            TileCauldron.this.markDirty();
        }
    };

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("itemsIn"))
            inputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
        if (compound.hasKey("itemsOut"))
            outputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsOut"));
        progress = compound.getInteger( "progress");
        if (compound.hasKey("chef")) chef = compound.getUniqueId("chef");
        fuelProgress = compound.getInteger("fuelProgress");
        loadItemList(compound,buckkets);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());
        compound.setInteger("progress", progress);
        if (chef !=null) compound.setUniqueId("chef", chef);
        compound.setInteger("fuelProgress",fuelProgress);
        saveItemList(compound,buckkets);
        return compound;
    }

    public NBTTagCompound saveItemList(NBTTagCompound tag, List<ItemStack> itemStacks)
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
    public void loadItemList(NBTTagCompound tag, List<ItemStack> itemStacks)
    {
        NBTTagList tagList = tag.getTagList("Items", 10);

        for(int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound compound = tagList.getCompoundTagAt(i);
            itemStacks.add(new ItemStack(compound));
        }
    }

    private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler,outputHandler);

    public boolean canInteractWith(EntityPlayer player){
        return player.getDistanceSq(pos.add(0.5D,0.5D,0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == null) return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
            else if (facing == EnumFacing.UP) return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);
            else return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputHandler);
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
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
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
}
