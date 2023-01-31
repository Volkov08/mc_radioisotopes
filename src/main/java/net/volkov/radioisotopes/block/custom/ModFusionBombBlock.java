package net.volkov.radioisotopes.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.volkov.radioisotopes.effect.ModEffects;

public class ModFusionBombBlock extends Block {
    public ModFusionBombBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState.isOf(state.getBlock())) {
            return;
        }
        if (world.isReceivingRedstonePower(pos)) {
            ModFusionBombBlock.primeNuke(world, pos);
        }
    }
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isReceivingRedstonePower(pos)) {
            ModFusionBombBlock.primeNuke(world, pos);
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

        for (int y = -80; y <= 60; y = y + 7) {
            for (int x = 0; x <= 360; x = x + 40) {
                int r = 12;
                world.createExplosion(null, pos.getX() + (r * Math.cos(x)), pos.getY() + y, pos.getZ() + (r * Math.sin(x)), 27.0f, false, Explosion.DestructionType.BREAK);
            }
        }
        for (int y = -45; y <= 40; y = y + 12) {
            for (int x = 0; x <= 300; x = x + 8) {
                int r = 38;
                world.createExplosion(null, pos.getX() + (r * Math.cos(x)), pos.getY() + y, pos.getZ() + (r * Math.sin(x)), 30.0f, false, Explosion.DestructionType.BREAK);
            }
        }
        for (int y = -28; y <= 30; y = y + 12) {
            for (int x = 0; x <= 350; x = x + 7) {
                int r = 58;
                world.createExplosion(null, pos.getX() + (r * Math.cos(x)), pos.getY() + y, pos.getZ() + (r * Math.sin(x)), 30.0f, false, Explosion.DestructionType.BREAK);
            }
        }
        for (int y = -20; y <= 30; y = y + 10) {
            for (int x = 0; x <= 350; x = x + 5) {
                int r = 72;
                world.createExplosion(null, pos.getX() + (r * Math.cos(x)), pos.getY() + y, pos.getZ() + (r * Math.sin(x)), 30.0f, false, Explosion.DestructionType.BREAK);
            }
        }
        for (int x = 0; x <= 135; x = x + 2) {
            int r = 83;
            world.createExplosion(null, pos.getX() + (r * Math.cos(x)), pos.getY(), pos.getZ() + (r * Math.sin(x)), 28.0f, true, Explosion.DestructionType.BREAK);
        }
        //AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(world, pos.getX(), pos.getY() - 86, pos.getZ());
        //areaEffectCloudEntity.setParticleType(ParticleTypes.SNEEZE);
        //areaEffectCloudEntity.setRadius(28.0f);
        //areaEffectCloudEntity.setDuration(10000);
        //areaEffectCloudEntity.addEffect(new StatusEffectInstance(ModEffects.RAD_POISON, 3000, 650));
        //world.spawnEntity(areaEffectCloudEntity);


    }
}
