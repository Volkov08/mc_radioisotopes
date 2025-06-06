package net.volkov.radioisotopes.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.volkov.radioisotopes.entity.ModEntities;
import net.volkov.radioisotopes.entity.NuclearExplosionEntity;

public class ModImplosionAtomicBombBlock extends Block {
    public ModImplosionAtomicBombBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState.isOf(state.getBlock())) {
            return;
        }
        if (world.isReceivingRedstonePower(pos)) {
            primeNuke(world, pos, 51, 7200d);
        }
    }
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isReceivingRedstonePower(pos)) {
            primeNuke(world, pos, 51, 7200d);
        }
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        primeNuke(world, pos, 20, 2300d);
    }

    private static void primeNuke(World world, BlockPos pos, int radius, double radiation) {
        if (world.isClient) {
            return;
        }
        NuclearExplosionEntity nuke = new NuclearExplosionEntity(ModEntities.NUCLEAR_EXPLOSION_ENTITY, world, radius, radiation);
        nuke.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
        world.spawnEntity(nuke);

        world.removeBlock(pos, false);
    }

}
