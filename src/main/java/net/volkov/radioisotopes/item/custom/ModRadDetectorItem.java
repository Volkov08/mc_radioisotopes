package net.volkov.radioisotopes.item.custom;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.volkov.radioisotopes.block.ModBlocks;

public class ModRadDetectorItem extends Item {

    public ModRadDetectorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && hand == Hand.OFF_HAND) {
            outputRad(user, world);
            user.getItemCooldownManager().set(this, 20);
        }

        return super.use(world, user, hand);
    }

    private void outputRad(PlayerEntity player, World world) {
        BlockPos pos = player.getBlockPos();
        Box box = new Box(pos).expand(15);

        for(BlockPos blockPos : BlockPos.iterate((int)box.minX, (int)box.minY, (int)box.minZ, (int)box.maxX, (int)box.maxY, (int)box.maxZ)) {
            Block c_block = world.getBlockState(blockPos).getBlock();
            if(c_block == ModBlocks.URANIUM_ORE || c_block == ModBlocks.DEEPSLATE_URANIUM_ORE || c_block == ModBlocks.URANIUM_BLOCK || c_block == ModBlocks.ENRICHED_URANIUM_BLOCK) {
                if(!world.isClient()) {
                    if(player instanceof ServerPlayerEntity) {
                        player.sendMessage(new LiteralText("Radiation detected"), false);
                    }
                }


            }
        }

    }
}
