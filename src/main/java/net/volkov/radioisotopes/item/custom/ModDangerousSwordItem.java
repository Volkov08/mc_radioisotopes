package net.volkov.radioisotopes.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class ModDangerousSwordItem extends SwordItem {
    public ModDangerousSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 300, 2), attacker);
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 1200, 3), attacker);
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 3), attacker);
        return super.postHit(stack, target, attacker);
    }

    /*
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.literal("**Powerful Sword with special effects**").formatted(Formatting.AQUA));
        } else {
            tooltip.add(Text.literal("Press [SHIFT] for infos!").formatted(Formatting.YELLOW));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
    */
}
