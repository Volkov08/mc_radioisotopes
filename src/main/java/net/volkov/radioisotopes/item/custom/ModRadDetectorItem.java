package net.volkov.radioisotopes.item.custom;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.volkov.radioisotopes.block.ModBlocks;
import net.volkov.radioisotopes.entity.ModEntities;
import net.volkov.radioisotopes.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ModRadDetectorItem extends Item {
    private int charge = 160;

    public ModRadDetectorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(!world.isClient() && hand == Hand.MAIN_HAND) {
            outputRad(player, world);
            player.getItemCooldownManager().set(this, 20);
        }
        return super.use(world, player, hand);
    }

    private void outputRad(PlayerEntity player, World world) {
        if (Objects.equals(player.getOffHandStack().getItem(), ModItems.FULL_LEAD_BATTERY) && this.charge < 160) {
            player.getInventory().removeStack(40);
            player.getInventory().setStack(40, new ItemStack(ModItems.LEAD_BATTERY));
            this.charge = 160;
        }
        else if(this.charge > 0) {
            boolean has_rad = false;
            BlockPos pos = player.getBlockPos();
            Box box = new Box(pos).expand(15);
            Box f_box = new Box(pos).expand(135);
            Box r_box = new Box(pos).expand(155);

            for (BlockPos blockPos : BlockPos.iterate((int) box.minX, (int) box.minY, (int) box.minZ, (int) box.maxX, (int) box.maxY, (int) box.maxZ)) {
                Block c_block = world.getBlockState(blockPos).getBlock();
                if (c_block == ModBlocks.URANIUM_ORE || c_block == ModBlocks.DEEPSLATE_URANIUM_ORE || c_block == ModBlocks.URANIUM_BLOCK || c_block == ModBlocks.ENRICHED_URANIUM_BLOCK) {
                    has_rad = true;
                    break;
                }
            }
            if (!has_rad) {
                for (Entity entity : world.getEntitiesByType(ModEntities.FISSION_RAD_ENTITY, f_box, entity -> true)) {
                    has_rad = true;
                    break;
                }
            }
            if (!has_rad) {
                for (Entity entity : world.getEntitiesByType(ModEntities.REACTOR_RAD_ENTITY, r_box, entity -> true)) {
                    has_rad = true;
                    break;
                }
            }
            if(player instanceof ServerPlayerEntity) {
                if(has_rad) {
                    player.sendMessage(Text.literal("Radiation detected"), false);
                }
                else {
                    player.sendMessage(Text.literal("No significant amount of radiation detected"), false);
                }

            }
            this.charge--;
        } else {
            if(player instanceof ServerPlayerEntity) {
                player.sendMessage(Text.literal("Battery low"), false);
            }
        }
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        String chargeString = String.format("%d", this.charge);
        MutableText chargeText = Text.translatable("tooltip.rad_detector.charge", chargeString);
        MutableText hintText_1 = Text.translatable("tooltip.rad_detector.hint_1");
        MutableText hintText_2 = Text.translatable("tooltip.rad_detector.hint_2");
        MutableText hintText_3 = Text.translatable("tooltip.rad_detector.hint_3");
        tooltip.add(chargeText);
        tooltip.add(hintText_1);
        tooltip.add(hintText_2);
        tooltip.add(hintText_3);
        super.appendTooltip(stack, world, tooltip, context);
    }
}
