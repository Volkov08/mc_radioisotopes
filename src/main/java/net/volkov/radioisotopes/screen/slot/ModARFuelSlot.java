package net.volkov.radioisotopes.screen.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.ClientOnly;
import net.volkov.radioisotopes.item.ModItemGroup;
import net.volkov.radioisotopes.item.ModItems;

public class ModARFuelSlot extends Slot {
    public ModARFuelSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }


    @Override
    public boolean canInsert(ItemStack stack) {
        return ModARFuelSlot.isFuelRod(stack);
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        return ModARFuelSlot.isFuelRod(stack) ? 64 : super.getMaxItemCount(stack);
    }

    public static boolean isFuelRod(ItemStack stack) {
        return stack.isOf(ModItems.NUCLEAR_FUEL_ROD);
    }
}