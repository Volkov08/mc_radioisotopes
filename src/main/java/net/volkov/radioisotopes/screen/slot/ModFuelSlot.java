package net.volkov.radioisotopes.screen.slot;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.volkov.radioisotopes.item.ModItems;

public class ModFuelSlot extends Slot {
    public ModFuelSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }


    @Override
    public boolean canInsert(ItemStack stack) {
        return ModFuelSlot.isPotato(stack);
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        return ModFuelSlot.isPotato(stack) ? 1 : super.getMaxItemCount(stack);
    }

    public static boolean isPotato(ItemStack stack) {
        if(stack.isOf(Items.POTATO) || stack.isOf(ModItems.FULL_LEAD_BATTERY)) {
            return true;
        }
        else {
            return false;
        }
    }
}