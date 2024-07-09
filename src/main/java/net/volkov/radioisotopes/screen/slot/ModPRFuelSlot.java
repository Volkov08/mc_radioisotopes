package net.volkov.radioisotopes.screen.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.volkov.radioisotopes.item.ModItems;

public class ModPRFuelSlot extends Slot {
    public ModPRFuelSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }


    @Override
    public boolean canInsert(ItemStack stack) {
        return ModPRFuelSlot.isEnergy(stack);
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        return super.getMaxItemCount(stack);
    }

    public static boolean isEnergy(ItemStack stack) {
        return stack.isOf(Items.POTATO) || stack.isOf(ModItems.FULL_LEAD_BATTERY);
    }
}