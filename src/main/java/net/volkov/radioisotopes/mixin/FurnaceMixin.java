package net.volkov.radioisotopes.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeUnlocker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.volkov.radioisotopes.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class FurnaceMixin extends LockableContainerBlockEntity implements SidedInventory,
        RecipeUnlocker,
        RecipeInputProvider {

    protected FurnaceMixin(BlockEntityType<? extends AbstractFurnaceBlockEntity> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private static void tick(World world, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo ci){
        if(world.getBlockState(pos.down()) == Blocks.HOPPER.getDefaultState() && blockEntity.getStack(1).getItem() == ModItems.LEAD_BATTERY) {
            HopperBlockEntity hopper = (HopperBlockEntity) world.getBlockEntity(pos.down());
            int slot = -1;
            for (int i = 0; i<=4; i++) {
                if (hopper.getStack(i).isEmpty() || (Objects.equals(hopper.getStack(i).getItem(), ModItems.LEAD_BATTERY) && hopper.getStack(i).getCount() < ModItems.LEAD_BATTERY.getMaxCount())) {
                    slot = i;
                    break;
                }
            }
            if (slot != -1) {
                blockEntity.removeStack(1);
                hopper.setStack(slot, new ItemStack(ModItems.LEAD_BATTERY, hopper.getStack(slot).getCount() + 1));
            }
        }
    }
}
