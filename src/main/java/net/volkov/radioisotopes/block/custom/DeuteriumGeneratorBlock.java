package net.volkov.radioisotopes.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class DeuteriumGeneratorBlock extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public DeuteriumGeneratorBlock(Settings settings) {
        super(settings);
    }

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.createCuboidShape(0, 0, 0, 16, 11, 16),
            Block.createCuboidShape(12, 11, 12, 12, 14, 14),
            Block.createCuboidShape(14, 11, 12, 14, 14, 14),
            Block.createCuboidShape(13, 11, 6, 14, 13, 7),
            Block.createCuboidShape(2, 11, 6, 3, 14, 7),
            Block.createCuboidShape(12, 11, 14, 14, 14, 14),
            Block.createCuboidShape(12, 11, 12, 14, 14, 12),
            Block.createCuboidShape(2, 11, 14, 4, 14, 14),
            Block.createCuboidShape(2, 11, 12, 2, 14, 14),
            Block.createCuboidShape(2, 11, 12, 4, 14, 12),
            Block.createCuboidShape(4, 11, 12, 4, 14, 14),
            Block.createCuboidShape(5, 11, 0, 11, 15, 4),
            Block.createCuboidShape(2, 13, 2, 3, 14, 6),
            Block.createCuboidShape(13, 12, 2, 14, 13, 6),
            Block.createCuboidShape(11, 12, 2, 13, 13, 3),
            Block.createCuboidShape(3, 13, 2, 5, 14, 3)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.createCuboidShape(0, 0, 0, 16, 11, 16),
            Block.createCuboidShape(2, 11, 12, 4, 14, 12),
            Block.createCuboidShape(2, 11, 14, 4, 14, 14),
            Block.createCuboidShape(9, 11, 13, 10, 13, 14),
            Block.createCuboidShape(9, 11, 2, 10, 14, 3),
            Block.createCuboidShape(2, 11, 12, 2, 14, 14),
            Block.createCuboidShape(4, 11, 12, 4, 14, 14),
            Block.createCuboidShape(2, 11, 2, 2, 14, 4),
            Block.createCuboidShape(2, 11, 2, 4, 14, 2),
            Block.createCuboidShape(4, 11, 2, 4, 14, 4),
            Block.createCuboidShape(2, 11, 4, 4, 14, 4),
            Block.createCuboidShape(12, 11, 5, 16, 15, 11),
            Block.createCuboidShape(10, 13, 2, 14, 14, 3),
            Block.createCuboidShape(10, 12, 13, 14, 13, 14),
            Block.createCuboidShape(13, 12, 11, 14, 13, 13),
            Block.createCuboidShape(13, 13, 3, 14, 14, 5)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();


    private static final VoxelShape SHAPE_S = Stream.of(
            Block.createCuboidShape(0, 0, 0, 16, 11, 16),
            Block.createCuboidShape(4, 11, 2, 4, 14, 4),
            Block.createCuboidShape(2, 11, 2, 2, 14, 4),
            Block.createCuboidShape(2, 11, 9, 3, 13, 10),
            Block.createCuboidShape(13, 11, 9, 14, 14, 10),
            Block.createCuboidShape(2, 11, 2, 4, 14, 2),
            Block.createCuboidShape(2, 11, 4, 4, 14, 4),
            Block.createCuboidShape(12, 11, 2, 14, 14, 2),
            Block.createCuboidShape(14, 11, 2, 14, 14, 4),
            Block.createCuboidShape(12, 11, 4, 14, 14, 4),
            Block.createCuboidShape(12, 11, 2, 12, 14, 4),
            Block.createCuboidShape(5, 11, 12, 11, 15, 16),
            Block.createCuboidShape(13, 13, 10, 14, 14, 14),
            Block.createCuboidShape(2, 12, 10, 3, 13, 14),
            Block.createCuboidShape(3, 12, 13, 5, 13, 14),
            Block.createCuboidShape(11, 13, 13, 13, 14, 14)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.createCuboidShape(0, 0, 0, 16, 11, 16),
            Block.createCuboidShape(12, 11, 4, 14, 14, 4),
            Block.createCuboidShape(12, 11, 2, 14, 14, 2),
            Block.createCuboidShape(6, 11, 2, 7, 13, 3),
            Block.createCuboidShape(6, 11, 13, 7, 14, 14),
            Block.createCuboidShape(14, 11, 2, 14, 14, 4),
            Block.createCuboidShape(12, 11, 2, 12, 14, 4),
            Block.createCuboidShape(14, 11, 12, 14, 14, 14),
            Block.createCuboidShape(12, 11, 14, 14, 14, 14),
            Block.createCuboidShape(12, 11, 12, 12, 14, 14),
            Block.createCuboidShape(12, 11, 12, 14, 14, 12),
            Block.createCuboidShape(0, 11, 5, 4, 15, 11),
            Block.createCuboidShape(2, 13, 13, 6, 14, 14),
            Block.createCuboidShape(2, 12, 2, 6, 13, 3),
            Block.createCuboidShape(2, 12, 3, 3, 13, 5),
            Block.createCuboidShape(2, 13, 11, 3, 14, 13)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case NORTH:
                return SHAPE_N;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            case EAST:
                return SHAPE_E;
            default:
                return SHAPE_N;
        }

    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
