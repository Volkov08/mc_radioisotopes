package net.volkov.radioisotopes.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.volkov.radioisotopes.entity.ModEntities;
import net.volkov.radioisotopes.entity.NuclearExplosionEntity;
import net.volkov.radioisotopes.entity.RadEntity;

public class ModDirtyBombBlock extends Block {
    public ModDirtyBombBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState.isOf(state.getBlock())) {
            return;
        }
        if (world.isReceivingRedstonePower(pos)) {
            primeNuke(world, pos);
        }
    }
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isReceivingRedstonePower(pos)) {
            primeNuke(world, pos);
        }
    }

    private static void primeNuke(World world, BlockPos pos) {
        if (world.isClient) {
            return;
        }
        
        world.removeBlock(pos, false);
        world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 4.0f, true, Explosion.DestructionType.BREAK);
        world.setBlockState(pos, Blocks.LAVA.getDefaultState());
        RadEntity rad = new RadEntity(ModEntities.RAD_ENTITY, world, 125000, 35d, 9400d, false);
        rad.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0);
        world.spawnEntity(rad);
    }
}
