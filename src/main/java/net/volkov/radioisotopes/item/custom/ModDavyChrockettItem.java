package net.volkov.radioisotopes.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.volkov.radioisotopes.entity.M388Entity;
import net.volkov.radioisotopes.item.ModItems;

import java.util.Objects;

public class ModDavyChrockettItem extends Item {
    public ModDavyChrockettItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(!world.isClient() && hand == Hand.MAIN_HAND) {
            if (Objects.equals(player.getOffHandStack().getItem(), ModItems.M388_NUCLEAR_ROUND)) {
                M388Entity round = new M388Entity(world, player);
                round.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, 4.0f, 0.0f);
                world.spawnEntity(round);
                if (!player.isCreative()) {
                    player.getInventory().removeStack(40, 1);
                }
                player.getItemCooldownManager().set(this, 65);
            }
        }
        return super.use(world, player, hand);
    }
}