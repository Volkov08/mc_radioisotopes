package net.volkov.radioisotopes.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.volkov.radioisotopes.entity.ModEntities;
import net.volkov.radioisotopes.entity.RadEntity;
import net.volkov.radioisotopes.item.ModItems;
import net.volkov.radioisotopes.item.inventory.ImplementedInventory;
import net.volkov.radioisotopes.recipe.AtomicReactorRecipe;
import net.volkov.radioisotopes.screen.AtomicReactorControllerScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AtomicReactorControllerBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(3, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 2400;
    public final int maxFuelTime = 80000;

    public AtomicReactorControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ATOMIC_REACTOR_CONTROLLER, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch (index) {
                    case 0: return AtomicReactorControllerBlockEntity.this.progress;
                    case 1: return AtomicReactorControllerBlockEntity.this.maxProgress;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: AtomicReactorControllerBlockEntity.this.progress = value; break;
                    case 1: AtomicReactorControllerBlockEntity.this.maxProgress = value; break;
                }
            }

            public int size() {
                return 4;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.radioisotopes.atomic_reactor_controller");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new AtomicReactorControllerScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("blaster.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("blaster.progress");
    }

    public static void tick(World world, BlockPos pos, BlockState state, AtomicReactorControllerBlockEntity entity) {
        if (!world.isClient()) {
            if (hasRecipe(entity)) {
                if (hasFuelInFuelSlot(entity) && consumeFuel(entity)) {
                    double x = Math.random();
                    if (x < 0.2573867 && x > 0.2573866) {
                        world.removeBlock(pos, false);
                        world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 4.0f, true, Explosion.DestructionType.BREAK);
                        world.setBlockState(pos, Blocks.LAVA.getDefaultState());
                        RadEntity rad = new RadEntity(ModEntities.RAD_ENTITY, world, 125d, 12000d, true);
                        rad.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0);
                        world.spawnEntity(rad);
                    }
                    entity.progress++;
                    if (entity.progress > entity.maxProgress) {
                        craftItem(entity);
                    }
                    markDirty(world, pos, state);
                }
            } else {
                entity.resetProgress();
                markDirty(world, pos, state);
            }
        }
    }

    private static boolean hasFuelInFuelSlot(AtomicReactorControllerBlockEntity entity) {
        return !entity.getStack(0).isEmpty();
    }

    private static boolean consumeFuel(AtomicReactorControllerBlockEntity entity) {
        if (entity.getStack(0).getItem() == ModItems.NUCLEAR_FUEL_STACK) {
            NbtCompound nbtData = new NbtCompound();
            if (entity.getStack(0).hasNbt()) {
                if (entity.getStack(0).getNbt().getInt("radioisotopes.depletion") < entity.maxFuelTime) {
                    nbtData.putInt("radioisotopes.depletion", entity.getStack(0).getNbt().getInt("radioisotopes.depletion") + 1);
                    entity.getStack(0).setNbt(nbtData);
                    return true;
                }
            } else {
                nbtData.putInt("radioisotopes.depletion", 1);
                entity.getStack(0).setNbt(nbtData);
                return true;
            }
        }
        return false;
    }

    private static boolean hasRecipe(AtomicReactorControllerBlockEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<AtomicReactorRecipe> match = world.getRecipeManager()
                .getFirstMatch(AtomicReactorRecipe.Type.INSTANCE, inventory, world);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getOutput());
    }

    private static void craftItem(AtomicReactorControllerBlockEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<AtomicReactorRecipe> match = world.getRecipeManager()
                .getFirstMatch(AtomicReactorRecipe.Type.INSTANCE, inventory, world);

        if(match.isPresent()) {
            entity.removeStack(1,1);

            entity.setStack(2, new ItemStack(match.get().getOutput().getItem(),
                    entity.getStack(2).getCount() + 1));

            entity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    //Beginning sided inv
    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        if(side == Direction.DOWN) {
            return false;
        }

        if(side == Direction.UP) {
            return slot == 1;
        }
        return slot == 0 && stack.isOf(ModItems.NUCLEAR_FUEL_STACK);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        if(side == Direction.DOWN) {
            if (slot == 0) {
                return stack.getItem() == ModItems.NUCLEAR_FUEL_STACK && stack.hasNbt() && stack.getNbt().getInt("fuel_stack.depletion") == maxFuelTime;
            }
            return slot == 2;
        }
        return false;
    }

    //End sided inv

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, ItemStack output) {
        return inventory.getStack(2).getItem() == output.getItem() || inventory.getStack(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(2).getMaxCount() > inventory.getStack(2).getCount();
    }

}