package net.volkov.radioisotopes.screen.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class ModBRFuelSlot extends Slot {
    public ModBRFuelSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }


    @Override
    public boolean canInsert(ItemStack stack) {
        return ModBRFuelSlot.isBlazeRod(stack);
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        return super.getMaxItemCount(stack);
    }

    public static boolean isBlazeRod(ItemStack stack) {
        return stack.isOf(Items.BLAZE_ROD);
    }
}