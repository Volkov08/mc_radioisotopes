package net.volkov.radioisotopes.item.custom;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.volkov.radioisotopes.block.ModBlocks;
import net.volkov.radioisotopes.entity.ModEntities;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModRadDetectorItem extends Item {
    private int charge = 160;

    public ModRadDetectorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if(charge > 0) {
            outputRad(player, world);
        }
        else {
            player.sendMessage(Text.literal("Battery low"), false);
        }
        return TypedActionResult.success(stack);
    }

    private void outputRad(PlayerEntity player, World world) {
        boolean has_rad = false;
        BlockPos pos = player.getBlockPos();
        Box box = new Box(pos).expand(15);
        Box f_box = new Box(pos).expand(140);
        Box r_box = new Box(pos).expand(155);

        for(BlockPos blockPos : BlockPos.iterate((int)box.minX, (int)box.minY, (int)box.minZ, (int)box.maxX, (int)box.maxY, (int)box.maxZ)) {
            Block c_block = world.getBlockState(blockPos).getBlock();
            if(c_block == ModBlocks.URANIUM_ORE || c_block == ModBlocks.DEEPSLATE_URANIUM_ORE || c_block == ModBlocks.URANIUM_BLOCK || c_block == ModBlocks.ENRICHED_URANIUM_BLOCK) {
                has_rad = true;
                break;
            }
        }
        if(!has_rad) {
            for(Entity entity : world.getEntitiesByType(ModEntities.FISSION_RAD_ENTITY, f_box, entity -> true)) {
                has_rad = true;
                break;
            }
        }
        if(!has_rad) {
            for(Entity entity : world.getEntitiesByType(ModEntities.REACTOR_RAD_ENTITY, r_box, entity -> true)) {
                has_rad = true;
                break;
            }
        }
        if(!world.isClient()) {
            if(player instanceof ServerPlayerEntity) {
                if(has_rad) {
                    player.sendMessage(Text.literal("Radiation detected"), false);
                }
                else {
                    player.sendMessage(Text.literal("No significant amount of radiation detected"), false);
                }

            }
        }
        charge--;
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        String chargeString = String.format("%d", charge);
        MutableText chargeText = Text.translatable("tooltip.rad_detector.charge", chargeString);
        tooltip.add(chargeText);
        super.appendTooltip(stack, world, tooltip, context);
    }
}
