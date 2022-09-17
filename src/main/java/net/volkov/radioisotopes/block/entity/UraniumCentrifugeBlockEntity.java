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
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.volkov.radioisotopes.block.custom.ModDeuteriumGeneratorBlock;
import net.volkov.radioisotopes.block.custom.ModUraniumCentrifugeBlock;
import net.volkov.radioisotopes.item.inventory.ImplementedInventory;
import net.volkov.radioisotopes.recipe.UraniumCentrifugeRecipe;
import net.volkov.radioisotopes.screen.UraniumCentrifugeScreenHandler;
import net.volkov.radioisotopes.screen.UraniumCentrifugeScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class UraniumCentrifugeBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(4, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 6000;
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
        return new LiteralText("Uranium Centrifuge");
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

        if(side == Direction.UP || side == Direction.DOWN) {
            return false;
        }

        // Top insert 1
        // Right insert 2
        // Left insert 0 (fuel)

        return switch (localDir) {
            default ->
                    side.getOpposite() == Direction.NORTH && slot == 1 ||
                            side.getOpposite() == Direction.EAST && slot == 2 ||
                            side.getOpposite() == Direction.WEST && slot == 0;
            case EAST ->
                    side.rotateYClockwise() == Direction.NORTH && slot == 1 ||
                            side.rotateYClockwise() == Direction.EAST && slot == 2 ||
                            side.rotateYClockwise() == Direction.WEST && slot == 0;
            case SOUTH ->
                    side == Direction.NORTH && slot == 1 ||
                            side == Direction.EAST && slot == 2 ||
                            side == Direction.WEST && slot == 0;
            case WEST ->
                    side.rotateYCounterclockwise() == Direction.NORTH && slot == 1 ||
                            side.rotateYCounterclockwise() == Direction.EAST && slot == 2 ||
                            side.rotateYCounterclockwise() == Direction.WEST && slot == 0;
        };
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(ModUraniumCentrifugeBlock.FACING);

        if(side == Direction.UP) {
            return false;
        }

        // Down extract 2
        if(side == Direction.DOWN) {
            return slot == 3;
        }

        // bottom extract 2
        // right extract 2
        return switch (localDir) {
            default -> side.getOpposite() == Direction.SOUTH && slot == 3 ||
                    side.getOpposite() == Direction.EAST && slot == 3;
            case EAST -> side.rotateYClockwise() == Direction.SOUTH && slot == 3 ||
                    side.rotateYClockwise() == Direction.EAST && slot == 3;
            case SOUTH -> side == Direction.SOUTH && slot == 3 ||
                    side == Direction.EAST && slot == 3;
            case WEST -> side.rotateYCounterclockwise() == Direction.SOUTH && slot == 3 ||
                    side.rotateYCounterclockwise() == Direction.EAST && slot == 3;
        };
    }

    //End sided inv


    private void consumeFuel() {
        if(!getStack(0).isEmpty()) {

            this.fuelTime = 2400;
            removeStack(0, 1);
            this.maxFuelTime = this.fuelTime;
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, UraniumCentrifugeBlockEntity entity) {
        if(isConsumingFuel(entity)) {
            entity.fuelTime--;
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
