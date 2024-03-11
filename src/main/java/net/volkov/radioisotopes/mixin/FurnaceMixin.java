package net.volkov.radioisotopes.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeUnlocker;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.volkov.radioisotopes.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class FurnaceMixin extends LockableContainerBlockEntity implements SidedInventory,
        RecipeUnlocker,
        RecipeInputProvider {
    @Shadow protected DefaultedList<ItemStack> inventory;

    protected FurnaceMixin(BlockEntityType<? extends AbstractFurnaceBlockEntity> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }
    
    @Inject(method = "canExtract", at = @At("HEAD"), cancellable = true)
    private void canExtractMixin(int slot, ItemStack stack, Direction dir, CallbackInfoReturnable<Boolean> cir) {
        if (dir == Direction.DOWN && slot == 1 && stack.isOf(ModItems.LEAD_BATTERY)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "isValid", at = @At("HEAD"), cancellable = true)
    private void isValidMixin(int slot, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (slot == 1 && stack.isOf(ModItems.LEAD_BATTERY)) {
            cir.setReturnValue(true);
        }
    }
}
