package net.volkov.radioisotopes.item.custom;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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
import net.volkov.radioisotopes.entity.RadEntity;
import net.volkov.radioisotopes.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ModRadDetectorItem extends Item {

    public ModRadDetectorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(!world.isClient() && hand == Hand.MAIN_HAND) {
            if (!player.getStackInHand(hand).hasNbt()) {
                NbtCompound nbtData = new NbtCompound();
                nbtData.putInt("radioisotopes.battery", 160);
                player.getStackInHand(hand).setNbt(nbtData);
            }
            outputRad(player, world, hand);
            player.getItemCooldownManager().set(this, 20);
        }
        return super.use(world, player, hand);
    }

    private void outputRad(PlayerEntity player, World world, Hand hand) {
        if (Objects.equals(player.getOffHandStack().getItem(), ModItems.FULL_LEAD_BATTERY) && player.getStackInHand(hand).getNbt().getInt("radioisotopes.battery") == 0) {
            player.getInventory().removeStack(40);
            player.getInventory().setStack(40, new ItemStack(ModItems.LEAD_BATTERY));
            player.getStackInHand(hand).setNbt(new NbtCompound());
        }
        else if (player.getStackInHand(hand).getNbt().getInt("radioisotopes.battery") > 0) {
            boolean has_rad = false;
            BlockPos pos = player.getBlockPos();
            Box box = new Box(pos).expand(15);
            Box f_box = new Box(pos).expand(153);

            for (BlockPos blockPos : BlockPos.iterate((int) box.minX, (int) box.minY, (int) box.minZ, (int) box.maxX, (int) box.maxY, (int) box.maxZ)) {
                if (blockPos.isWithinDistance(player.getPos(), 15d)) {
                    Block c_block = world.getBlockState(blockPos).getBlock();
                    if (c_block == ModBlocks.URANIUM_ORE || c_block == ModBlocks.DEEPSLATE_URANIUM_ORE || c_block == ModBlocks.URANIUM_BLOCK) {
                        has_rad = true;
                        break;
                    }
                }
            }
            if (!has_rad) {
                for (RadEntity entity : world.getEntitiesByType(ModEntities.RAD_ENTITY, f_box, entity -> true)) {
                    if (entity.getPos().distanceTo(player.getPos()) <= entity.distance + 28d) {
                        has_rad = true;
                        break;
                    }
                }
            }
            if (player instanceof ServerPlayerEntity) {
                if (has_rad) {
                    player.sendMessage(Text.translatable("item.radioisotopes.rad_detector.rad_det"), false);
                }
                else {
                    player.sendMessage(Text.translatable("item.radioisotopes.rad_detector.rad_n_det"), false);
                }

            }

            NbtCompound nbtData = new NbtCompound();
            nbtData.putInt("radioisotopes.battery", player.getStackInHand(hand).getNbt().getInt("radioisotopes.battery") - 1);
            player.getStackInHand(hand).setNbt(nbtData);

        } else {
            if (player instanceof ServerPlayerEntity) {
                player.sendMessage(Text.translatable("item.radioisotopes.batt_low"), false);
            }
        }
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt()) {
            String chargeString = String.format("%d", stack.getNbt().getInt("radioisotopes.battery"));
            tooltip.add(Text.translatable("tooltip.rad_detector.charge", chargeString));
        }
        MutableText hintText_1 = Text.translatable("tooltip.rad_detector.hint_1");
        MutableText hintText_2 = Text.translatable("tooltip.rad_detector.hint_2");
        MutableText hintText_3 = Text.translatable("tooltip.rad_detector.hint_3");
        tooltip.add(hintText_1);
        tooltip.add(hintText_2);
        tooltip.add(hintText_3);
    }
}
