package net.volkov.radioisotopes.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModFuelStackItem extends Item {
    public ModFuelStackItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt()) {
            String chargeString = String.format("%d", stack.getNbt().getInt("radioisotopes.depletion")) + " / 80000";
            tooltip.add(Text.translatable("tooltip.fuel_stack.depletion", chargeString));
        }
    }
}
