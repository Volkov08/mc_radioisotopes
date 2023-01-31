package net.volkov.radioisotopes.block.custom;

import java.util.concurrent.TimeUnit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class ModAtomicBombBlock extends Block {
    public ModAtomicBombBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState.isOf(state.getBlock())) {
            return;
        }
        if (world.isReceivingRedstonePower(pos)) {
            ModAtomicBombBlock.primeNuke(world, pos);
        }
    }
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isReceivingRedstonePower(pos)) {
            ModAtomicBombBlock.primeNuke(world, pos);
        }
    }

    private static void primeNuke(World world, BlockPos pos) {
        if (world.isClient) {
            return;
        }
        world.removeBlock(pos, false);
        world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 30.0f, false, Explosion.DestructionType.BREAK);

        LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
        lightning.setPosition(pos.getX(), pos.getY(), pos.getZ());
        world.spawnEntity(lightning);

        world.playSound(null, pos, SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.BLOCKS, 50f, 1f);

        //for (int r = 7; r <= 140; r = r + 5) {
        //    for (int x = 0; x <= 260; x = x + 4) {
        //        world.createExplosion(null, pos.getX() + (r * Math.cos(x)), pos.getY() + 4, pos.getZ() + (r * Math.sin(x)), 5.0f, false, Explosion.DestructionType.BREAK);
        //    }
        //}

        for (int y = -50; y <= 60; y = y + 7) {
            for (int x = 0; x <= 275; x = x + 55) {
                int r = 5;
                world.createExplosion(null, pos.getX() + (r * Math.cos(x)), pos.getY() + y, pos.getZ() + (r * Math.sin(x)), 25.0f, false, Explosion.DestructionType.BREAK);
            }
        }
        for (int y = -25; y <= 40; y = y + 12) {
            for (int x = 0; x <= 250; x = x + 8) {
                int r = 30;
                world.createExplosion(null, pos.getX() + (r * Math.cos(x)), pos.getY() + y, pos.getZ() + (r * Math.sin(x)), 30.0f, false, Explosion.DestructionType.BREAK);
            }
        }
        for (int x = 0; x <= 135; x = x + 6) {
            int r = 55;
            world.createExplosion(null, pos.getX() + (r * Math.cos(x)), pos.getY(), pos.getZ() + (r * Math.sin(x)), 24.0f, true, Explosion.DestructionType.BREAK);
        }


    }
}
