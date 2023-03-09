package net.volkov.radioisotopes.block.custom;

import java.util.concurrent.TimeUnit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TntBlock;
import net.minecraft.client.particle.ParticleGroup;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
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
import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.effect.ModEffects;
import net.volkov.radioisotopes.entity.FissionExplosionEntity;
import net.volkov.radioisotopes.entity.FissionRadEntity;
import net.volkov.radioisotopes.entity.ModEntities;
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
        FissionExplosionEntity nuke = new FissionExplosionEntity(ModEntities.FISSION_EXPLOSION_ENTITY, world);
        nuke.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
        world.spawnEntity(nuke);

        world.removeBlock(pos, false);
        //world.setBlockState(pos, Blocks.LAVA.getDefaultState());
        //for (int r = 7; r <= 70; r = r + 5) {
        //    for (int x = 0; x <= 260; x = x + 4) {
                //world.createExplosion(null, pos.getX() + (r * Math.cos(x)), pos.getY() + 4, pos.getZ() + (r * Math.sin(x)), 5.0f, false, Explosion.DestructionType.BREAK);
        //        world.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + (r * Math.cos(x)), pos.getY() + 0.5D, pos.getZ() + (r * Math.cos(x)), 0.0D, 0.0D, 0.0D);
        //    }
        //}


    }

}
