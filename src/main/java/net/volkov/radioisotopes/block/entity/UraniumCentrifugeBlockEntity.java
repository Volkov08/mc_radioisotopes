package net.volkov.radioisotopes.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.volkov.radioisotopes.block.custom.ModUraniumCentrifugeBlock;
import net.volkov.radioisotopes.item.ModItems;
import net.volkov.radioisotopes.item.inventory.ImplementedInventory;
import net.volkov.radioisotopes.recipe.UraniumCentrifugeRecipe;
import net.volkov.radioisotopes.screen.UraniumCentrifugeScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class UraniumCentrifugeBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(4, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 5000;
    private int fuelTime = 0;
    private int maxFuelTime = 0;

    public UraniumCentrifugeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.URANIUM_CENTRIFUGE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch (index) {
                    case 0: return UraniumCentrifugeBlockEntity.this.progress;
                    case 1: return UraniumCentrifugeBlockEntity.this.maxProgress;
                    case 2: return UraniumCentrifugeBlockEntity.this.fuelTime;
                    case 3: return UraniumCentrifugeBlockEntity.this.maxFuelTime;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: UraniumCentrifugeBlockEntity.this.progress = value; break;
                    case 1: UraniumCentrifugeBlockEntity.this.maxProgress = value; break;
                    case 2: UraniumCentrifugeBlockEntity.this.fuelTime = value; break;
                    case 3: UraniumCentrifugeBlockEntity.this.maxFuelTime = value; break;
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
        return Text.translatable("block.radioisotopes.uranium_centrifuge");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new UraniumCentrifugeScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("centrifuge.progress", progress);
        nbt.putInt("centrifuge.fuelTime", fuelTime);
        nbt.putInt("centrifuge.maxFuelTime", maxFuelTime);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("centrifuge.progress");
        fuelTime = nbt.getInt("centrifuge.fuelTime");
        maxFuelTime = nbt.getInt("centrifuge.maxFuelTime");
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(ModUraniumCentrifugeBlock.FACING);
        EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
        DoubleBlockHalf uc = this.getWorld().getBlockState(this.pos).get(HALF);
        if (uc == DoubleBlockHalf.LOWER) {
            if (side == Direction.UP || side == Direction.DOWN) {
                return false;
            }

            // Top insert 0 (fuel)
            // Right insert 1
            // Left insert 2

            return switch (localDir) {
                default -> side.getOpposite() == Direction.NORTH && slot == 2 ||
                        side.getOpposite() == Direction.EAST && slot == 0 && (stack.isOf(ModItems.LEAD_BATTERY) || stack.isOf(ModItems.FULL_LEAD_BATTERY) || stack.isOf(Items.POTATO)) ||
                        side.getOpposite() == Direction.SOUTH && slot == 1;
                case EAST -> side.rotateYClockwise() == Direction.NORTH && slot == 2 ||
                        side.rotateYClockwise() == Direction.EAST && slot == 0 && (stack.isOf(ModItems.LEAD_BATTERY) || stack.isOf(ModItems.FULL_LEAD_BATTERY) || stack.isOf(Items.POTATO)) ||
                        side.rotateYClockwise() == Direction.SOUTH && slot == 1;
                case SOUTH -> side == Direction.NORTH && slot == 2 ||
                        side == Direction.EAST && slot == 0 ||
                        side == Direction.SOUTH && slot == 1;
                case WEST -> side.rotateYCounterclockwise() == Direction.NORTH && slot == 2 ||
                        side.rotateYCounterclockwise() == Direction.EAST && slot == 0 && (stack.isOf(ModItems.LEAD_BATTERY) || stack.isOf(ModItems.FULL_LEAD_BATTERY) || stack.isOf(Items.POTATO)) ||
                        side.rotateYCounterclockwise() == Direction.SOUTH && slot == 1;
            };
        } else {
            return false;
        }
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        if(side == Direction.DOWN) {
            if (slot == 0) {
                return stack.isOf(ModItems.LEAD_BATTERY);
            }
            return slot == 3;
        }
        return false;
    }

    //End sided inv


    private void consumeFuel() {
        if(getStack(0).getItem() == ModItems.FULL_LEAD_BATTERY) {
            this.fuelTime = 30000;
            removeStack(0, 1);
            setStack(0, new ItemStack(ModItems.LEAD_BATTERY, getStack(0).getCount() + 1));
            this.maxFuelTime = this.fuelTime;

        }
        else if(getStack(0).getItem() == Items.POTATO) {
            this.fuelTime = 100;
            removeStack(0, 1);
            this.maxFuelTime = this.fuelTime;
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, UraniumCentrifugeBlockEntity entity) {
        if (!world.isClient()) {
            if (isConsumingFuel(entity)) {
                entity.fuelTime--;
                markDirty(world, pos, state);
            }

            if (hasRecipe(entity)) {
                if (hasFuelInFuelSlot(entity) && !isConsumingFuel(entity)) {
                    entity.consumeFuel();
                    markDirty(world, pos, state);
                }
                if (isConsumingFuel(entity)) {
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

    private static boolean hasFuelInFuelSlot(UraniumCentrifugeBlockEntity entity) {
        return !entity.getStack(0).isEmpty();
    }

    private static boolean isConsumingFuel(UraniumCentrifugeBlockEntity entity) {
        return entity.fuelTime > 0;
    }

    private static boolean hasRecipe(UraniumCentrifugeBlockEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<UraniumCentrifugeRecipe> match = world.getRecipeManager()
                .getFirstMatch(UraniumCentrifugeRecipe.Type.INSTANCE, inventory, world);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getOutput());
    }

    private static void craftItem(UraniumCentrifugeBlockEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<UraniumCentrifugeRecipe> match = world.getRecipeManager()
                .getFirstMatch(UraniumCentrifugeRecipe.Type.INSTANCE, inventory, world);

        if(match.isPresent()) {
            entity.removeStack(1,1);
            entity.removeStack(2,1);

            entity.setStack(3, new ItemStack(match.get().getOutput().getItem(),
                    entity.getStack(3).getCount() + 1));

            entity.resetProgress();
        }
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, ItemStack output) {
        return inventory.getStack(3).getItem() == output.getItem() || inventory.getStack(3).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(3).getMaxCount() > inventory.getStack(3).getCount();
    }

    private void resetProgress() {
        this.progress = 0;
    }
}