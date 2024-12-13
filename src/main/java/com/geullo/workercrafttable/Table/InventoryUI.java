package com.geullo.workercrafttable.Table;

import com.geullo.workercrafttable.Block.utils.BlockRegistry;
import com.life.item.item.ItemInit;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryUI extends GuiInventory {

    private List<ItemStack> allowCraft = new ArrayList<>();
    private boolean craftAllowed = false;


    public InventoryUI(EntityPlayer player) {
        super(player);
        allowCraft.add(new ItemStack(ItemInit.glass));
        allowCraft.add(new ItemStack(Item.getItemFromBlock(BlockRegistry.oakTong)));
        allowCraft.add(new ItemStack(ItemInit.instance_kimchi));
        allowCraft.add(new ItemStack(Item.getItemFromBlock(BlockRegistry.potStand)));
    };

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (this.inventorySlots.getSlot(0).getHasStack()&&
                !Objects.requireNonNull(Item.REGISTRY.getNameForObject(
                        this.inventorySlots.getSlot(0).getStack().getItem()
                )).getResourceDomain().equals("minecraft")
               ||
                this.inventorySlots.getSlot(0).getStack().getUnlocalizedName().equals("tile.air")
        ) {
            if (allowCraft.stream().noneMatch(key->
                    key.getItem() == this.inventorySlots.getSlot(0).getStack().getItem() &&
                    key.getMetadata() == this.inventorySlots.getSlot(0).getStack().getMetadata())
            || this.inventorySlots.getSlot(0).getStack().getUnlocalizedName().equals("tile.air"))
            {
                this.inventorySlots.putStackInSlot(0,ItemStack.EMPTY);
                craftAllowed = false;
            }
            else {
                craftAllowed = true;
            }
        }
        else {
            craftAllowed = true;
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
