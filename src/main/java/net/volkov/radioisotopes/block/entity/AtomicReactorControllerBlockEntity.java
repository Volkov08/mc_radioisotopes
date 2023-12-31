package net.volkov.radioisotopes.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
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
import net.volkov.radioisotopes.block.custom.ModAtomicReactorControllerBlock;
import net.volkov.radioisotopes.entity.ModEntities;
import net.volkov.radioisotopes.entity.RadEntity;
import net.volkov.radioisotopes.item.ModItems;
import net.volkov.radioisotopes.item.inventory.ImplementedInventory;
import net.volkov.radioisotopes.recipe.AtomicReactorRecipe;
import net.volkov.radioisotopes.screen.AtomicReactorControllerScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class AtomicReactorControllerBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(3, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 2400;
    private int fuelTime = 0;
    private int maxFuelTime = 0;

    public AtomicReactorControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ATOMIC_REACTOR_CONTROLLER, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch (index) {
                    case 0: return AtomicReactorControllerBlockEntity.this.progress;
                    case 1: return AtomicReactorControllerBlockEntity.this.maxProgress;
                    case 2: return AtomicReactorControllerBlockEntity.this.fuelTime;
                    case 3: return AtomicReactorControllerBlockEntity.this.maxFuelTime;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: AtomicReactorControllerBlockEntity.this.progress = value; break;
                    case 1: AtomicReactorControllerBlockEntity.this.maxProgress = value; break;
                    case 2: AtomicReactorControllerBlockEntity.this.fuelTime = value; break;
                    case 3: AtomicReactorControllerBlockEntity.this.maxFuelTime = value; break;
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
        nbt.putInt("blaster.fuelTime", fuelTime);
        nbt.putInt("blaster.maxFuelTime", maxFuelTime);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("blaster.progress");
        fuelTime = nbt.getInt("blaster.fuelTime");
        maxFuelTime = nbt.getInt("blaster.maxFuelTime");
    }

    private void consumeFuel() {
        if(getStack(0).getItem() == ModItems.NUCLEAR_FUEL_ROD) {
            this.fuelTime = 32000;
            removeStack(0, 1);
            setStack(0, new ItemStack(ModItems.ATOMIC_WASTE, getStack(0).getCount() + 1));
            this.maxFuelTime = this.fuelTime;
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, AtomicReactorControllerBlockEntity entity) {
        if(world.getBlockState(pos.down()) == Blocks.HOPPER.getDefaultState() && entity.getStack(0).getItem() == ModItems.ATOMIC_WASTE) {
            HopperBlockEntity hopper = (HopperBlockEntity) world.getBlockEntity(pos.down());
            int slot = -1;
            for (int i = 0; i<=4; i++) {
                if (hopper.getStack(i).isEmpty() || (Objects.equals(hopper.getStack(i).getItem(), ModItems.ATOMIC_WASTE) && hopper.getStack(i).getCount() < ModItems.ATOMIC_WASTE.getMaxCount())) {
                    slot = i;
                    break;
                }
            }
            if (slot != -1) {
                entity.removeStack(0);
                hopper.setStack(slot, new ItemStack(ModItems.ATOMIC_WASTE, hopper.getStack(slot).getCount() + 1));
            }
        }

        if(isConsumingFuel(entity)) {
            entity.fuelTime--;
            if (!world.isClient()) {
                double x = Math.random();
                if (x < 0.2573867 && x > 0.2573866) {
                    world.removeBlock(pos, false);
                    world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 4.0f, true, Explosion.DestructionType.BREAK);
                    world.setBlockState(pos, Blocks.LAVA.getDefaultState());
                    RadEntity rad = new RadEntity(ModEntities.RAD_ENTITY, world, 125000, 125d, 9400d);
                    rad.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0);
                    world.spawnEntity(rad);
                }
            }
        }

        if(hasRecipe(entity)) {
            if(hasFuelInFuelSlot(entity) && !isConsumingFuel(entity)) {
                entity.consumeFuel();
            }
            if(isConsumingFuel(entity)) {
                entity.progress++;
                if(entity.progress > entity.maxProgress) {
                    craftItem(entity);
                }
            }
        } else {
            entity.resetProgress();
        }
    }

    private static boolean hasFuelInFuelSlot(AtomicReactorControllerBlockEntity entity) {
        return !entity.getStack(0).isEmpty();
    }

    private static boolean isConsumingFuel(AtomicReactorControllerBlockEntity entity) {
        return entity.fuelTime > 0;
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
        Direction localDir = this.getWorld().getBlockState(this.pos).get(ModAtomicReactorControllerBlock.FACING);

        if(side == Direction.DOWN) {
            return false;
        }

        if(side == Direction.UP) {
            return slot == 1;
        }
        return slot == 0;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(ModAtomicReactorControllerBlock.FACING);
        if(side == Direction.DOWN) {
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
