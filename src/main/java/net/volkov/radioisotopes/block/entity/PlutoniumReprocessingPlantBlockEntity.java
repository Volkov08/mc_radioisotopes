package net.volkov.radioisotopes.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
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
import net.volkov.radioisotopes.block.custom.ModPlutoniumReprocessingPlantBlock;
import net.volkov.radioisotopes.block.custom.ModUraniumCentrifugeBlock;
import net.volkov.radioisotopes.item.ModItems;
import net.volkov.radioisotopes.item.inventory.ImplementedInventory;
import net.volkov.radioisotopes.recipe.PlutoniumReprocessingPlantRecipe;
import net.volkov.radioisotopes.recipe.UraniumCentrifugeRecipe;
import net.volkov.radioisotopes.screen.PlutoniumReprocessingPlantScreenHandler;
import net.volkov.radioisotopes.screen.UraniumCentrifugeScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PlutoniumReprocessingPlantBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(4, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 3000;
    private int fuelTime = 0;
    private int maxFuelTime = 0;

    public PlutoniumReprocessingPlantBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLUTONIUM_REPROCESSING_PLANT, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch (index) {
                    case 0: return PlutoniumReprocessingPlantBlockEntity.this.progress;
                    case 1: return PlutoniumReprocessingPlantBlockEntity.this.maxProgress;
                    case 2: return PlutoniumReprocessingPlantBlockEntity.this.fuelTime;
                    case 3: return PlutoniumReprocessingPlantBlockEntity.this.maxFuelTime;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: PlutoniumReprocessingPlantBlockEntity.this.progress = value; break;
                    case 1: PlutoniumReprocessingPlantBlockEntity.this.maxProgress = value; break;
                    case 2: PlutoniumReprocessingPlantBlockEntity.this.fuelTime = value; break;
                    case 3: PlutoniumReprocessingPlantBlockEntity.this.maxFuelTime = value; break;
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
        return Text.translatable("block.radioisotopes.plutonium_reprocessing_plant");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new PlutoniumReprocessingPlantScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("prp.progress", progress);
        nbt.putInt("prp.fuelTime", fuelTime);
        nbt.putInt("prp.maxFuelTime", maxFuelTime);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("prp.progress");
        fuelTime = nbt.getInt("prp.fuelTime");
        maxFuelTime = nbt.getInt("prp.maxFuelTime");
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(ModPlutoniumReprocessingPlantBlock.FACING);
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

    public static void tick(World world, BlockPos pos, BlockState state, PlutoniumReprocessingPlantBlockEntity entity) {
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

    private static boolean hasFuelInFuelSlot(PlutoniumReprocessingPlantBlockEntity entity) {
        return !entity.getStack(0).isEmpty();
    }

    private static boolean isConsumingFuel(PlutoniumReprocessingPlantBlockEntity entity) {
        return entity.fuelTime > 0;
    }

    private static boolean hasRecipe(PlutoniumReprocessingPlantBlockEntity entity) {
        if (entity.getStack(1).getItem() == ModItems.NUCLEAR_FUEL_STACK && entity.getStack(2).getItem() == ModItems.BISMUTH_INGOT) {
            if (entity.getStack(1).hasNbt() && entity.getStack(1).getNbt().getInt("radioisotopes.depletion") > 75000) {
                return canInsertAmountIntoOutputSlot(entity, 3)
                        && canInsertItemIntoOutputSlot(entity, ModItems.FUEL_GRADE_PLUTONIUM_INGOT);
            } else if (entity.getStack(1).hasNbt() && entity.getStack(1).getNbt().getInt("radioisotopes.depletion") > 50000) {
                return canInsertAmountIntoOutputSlot(entity, 2)
                        && canInsertItemIntoOutputSlot(entity, ModItems.FUEL_GRADE_PLUTONIUM_INGOT);
            } else if (entity.getStack(1).hasNbt() && entity.getStack(1).getNbt().getInt("radioisotopes.depletion") > 25000) {
                return canInsertAmountIntoOutputSlot(entity, 1)
                        && canInsertItemIntoOutputSlot(entity, ModItems.WEAPONS_GRADE_PLUTONIUM_INGOT);
            }
        }
        return false;
    }

    private static void craftItem(PlutoniumReprocessingPlantBlockEntity entity) {
        if (entity.getStack(1).hasNbt() && entity.getStack(1).getNbt().getInt("radioisotopes.depletion") >= 75000) {
            entity.setStack(3, new ItemStack(ModItems.FUEL_GRADE_PLUTONIUM_INGOT,
                    entity.getStack(3).getCount() + 3));
        } else if (entity.getStack(1).hasNbt() && entity.getStack(1).getNbt().getInt("radioisotopes.depletion") >= 50000) {
            entity.setStack(3, new ItemStack(ModItems.WEAPONS_GRADE_PLUTONIUM_INGOT,
                    entity.getStack(3).getCount() + 2));
        } else if (entity.getStack(1).hasNbt() && entity.getStack(1).getNbt().getInt("radioisotopes.depletion") >= 25000) {
            entity.setStack(3, new ItemStack(ModItems.WEAPONS_GRADE_PLUTONIUM_INGOT,
                    entity.getStack(3).getCount() + 1));
        }
        entity.removeStack(1,1);
        entity.removeStack(2,1);
        entity.resetProgress();
    }

    private static boolean canInsertItemIntoOutputSlot(PlutoniumReprocessingPlantBlockEntity entity, Item output) {
        return entity.getStack(3).getItem() == output || entity.getStack(3).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(PlutoniumReprocessingPlantBlockEntity entity, int amount) {
        return entity.getStack(3).getMaxCount() > entity.getStack(3).getCount() + amount;
    }

    private void resetProgress() {
        this.progress = 0;
    }
}