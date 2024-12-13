package com.geullo.workercrafttable.Table;

import com.life.item.item.ItemInit;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CraftTableUI extends GuiCrafting {

    protected List<ItemStack> allowCraft = new ArrayList<>();
    private boolean craftAllowed = false;

    public CraftTableUI(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition) {
        super(playerInv, worldIn, blockPosition);
        allowCraft.add(new ItemStack(ItemInit.glass));
        allowCraft.add(new ItemStack(ItemInit.instance_kimchi));
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        craftAllowed = true;
        if (
                !Item.REGISTRY.getNameForObject(
                        ((ContainerWorkbench) this.inventorySlots).craftResult.getStackInSlot(0).getItem()
                ).getResourceDomain().equals("minecraft") || ((ContainerWorkbench) this.inventorySlots).craftResult.getStackInSlot(0).getUnlocalizedName().equals("tile.air")
        ) {
            if (allowCraft.stream().noneMatch(key->
                    key.getItem() == ((ContainerWorkbench) this.inventorySlots).craftResult.getStackInSlot(0).getItem() &&
                    key.getMetadata() == ((ContainerWorkbench) this.inventorySlots).craftResult.getStackInSlot(0).getMetadata())
            || ((ContainerWorkbench) this.inventorySlots).craftResult.getStackInSlot(0).getUnlocalizedName().equals("tile.air")
            ) {
                ((ContainerWorkbench) this.inventorySlots).craftResult.setInventorySlotContents(0, ItemStack.EMPTY);
                craftAllowed = false;
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void handleInput() throws IOException {
        if (getSlotUnderMouse()!=null&&!craftAllowed&&getSlotUnderMouse().slotNumber==0) return;
        super.handleInput();
    }

    @Override
    protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
        if (!craftAllowed&&slotId==0) return;
        super.handleMouseClick(slotIn, slotId, mouseButton, type);
    }
}
