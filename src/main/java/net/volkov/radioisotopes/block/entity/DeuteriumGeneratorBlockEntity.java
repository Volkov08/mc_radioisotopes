package net.volkov.radioisotopes.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
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
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.volkov.radioisotopes.item.ModItems;
import net.volkov.radioisotopes.item.inventory.ImplementedInventory;
import net.volkov.radioisotopes.recipe.DeuteriumGeneratorRecipe;
import net.volkov.radioisotopes.screen.DeuteriumGeneratorScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DeuteriumGeneratorBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(3, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 3000;
    private int fuelTime = 0;
    private int maxFuelTime = 0;

    public DeuteriumGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DEUTERIUM_GENERATOR, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch (index) {
                    case 0: return DeuteriumGeneratorBlockEntity.this.progress;
                    case 1: return DeuteriumGeneratorBlockEntity.this.maxProgress;
                    case 2: return DeuteriumGeneratorBlockEntity.this.fuelTime;
                    case 3: return DeuteriumGeneratorBlockEntity.this.maxFuelTime;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: DeuteriumGeneratorBlockEntity.this.progress = value; break;
                    case 1: DeuteriumGeneratorBlockEntity.this.maxProgress = value; break;
                    case 2: DeuteriumGeneratorBlockEntity.this.fuelTime = value; break;
                    case 3: DeuteriumGeneratorBlockEntity.this.maxFuelTime = value; break;
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
        return Text.translatable("block.radioisotopes.deuterium_generator");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new DeuteriumGeneratorScreenHandler(syncId, inv, this, this.propertyDelegate);
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

    public static void tick(World world, BlockPos pos, BlockState state, DeuteriumGeneratorBlockEntity entity) {
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

    private static boolean hasFuelInFuelSlot(DeuteriumGeneratorBlockEntity entity) {
        return !entity.getStack(0).isEmpty();
    }

    private static boolean isConsumingFuel(DeuteriumGeneratorBlockEntity entity) {
        return entity.fuelTime > 0;
    }

    private static boolean hasRecipe(DeuteriumGeneratorBlockEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<DeuteriumGeneratorRecipe> match = world.getRecipeManager()
                .getFirstMatch(DeuteriumGeneratorRecipe.Type.INSTANCE, inventory, world);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getOutput());
    }

    private static void craftItem(DeuteriumGeneratorBlockEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<DeuteriumGeneratorRecipe> match = world.getRecipeManager()
                .getFirstMatch(DeuteriumGeneratorRecipe.Type.INSTANCE, inventory, world);

        if(match.isPresent()) {
            if (entity.getStack(1).getItem().hasRecipeRemainder()) {
                entity.setStack(1, new ItemStack(entity.getStack(1).getItem().getRecipeRemainder()));
            } else {
                entity.removeStack(1,1);
            }

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
        return slot == 0 && (stack.isOf(ModItems.LEAD_BATTERY) || stack.isOf(ModItems.FULL_LEAD_BATTERY) || stack.isOf(Items.POTATO));
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        if(side == Direction.DOWN) {
            if (slot == 0) {
                return stack.isOf(ModItems.LEAD_BATTERY);
            }
            if (slot == 1) {
                return stack.isOf(Items.BUCKET);
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