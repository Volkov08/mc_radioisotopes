package net.volkov.radioisotopes.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.volkov.radioisotopes.entity.ModEntities;
import net.volkov.radioisotopes.entity.NuclearExplosionEntity;

public class ModBoostedImplosionAtomicBombBlock extends Block {
    public ModBoostedImplosionAtomicBombBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState.isOf(state.getBlock())) {
            return;
        }
        if (world.isReceivingRedstonePower(pos)) {
            ModBoostedImplosionAtomicBombBlock.primeNuke(world, pos);
        }
    }
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isReceivingRedstonePower(pos)) {
            ModBoostedImplosionAtomicBombBlock.primeNuke(world, pos);
        }
    }

    private static void primeNuke(World world, BlockPos pos) {
        if (world.isClient) {
            return;
        }
        NuclearExplosionEntity nuke = new NuclearExplosionEntity(ModEntities.NUCLEAR_EXPLOSION_ENTITY, world, 62, 5500d);
        nuke.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
        world.spawnEntity(nuke);

        world.removeBlock(pos, false);
    }

}
