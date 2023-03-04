package net.volkov.radioisotopes.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.block.custom.ModAtomicReactorControllerBlock;
import net.volkov.radioisotopes.block.custom.ModDeuteriumGeneratorBlock;
import net.volkov.radioisotopes.effect.ModEffects;
import net.volkov.radioisotopes.entity.FissionRadEntity;
import net.volkov.radioisotopes.item.ModItems;
import net.volkov.radioisotopes.item.inventory.ImplementedInventory;
import net.volkov.radioisotopes.recipe.AtomicReactorRecipe;
import net.volkov.radioisotopes.recipe.DeuteriumGeneratorRecipe;
import net.volkov.radioisotopes.screen.AtomicReactorControllerScreenHandler;
import net.volkov.radioisotopes.screen.DeuteriumGeneratorScreenHandler;
import org.jetbrains.annotations.Nullable;

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
        return new TranslatableText("block.radioisotopes.atomic_reactor_controller");
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
        if(entity.getStack(0).getItem() == ModItems.ATOMIC_WASTE && entity.getStack(2).isEmpty()) {
            entity.removeStack(0, 1);
            entity.setStack(2, new ItemStack(ModItems.ATOMIC_WASTE, 1));
        }

        if(isConsumingFuel(entity)) {
            entity.fuelTime--;
            if (!world.isClient()) {
                double x = Math.random();
                if (x < 0.2573867 && x > 0.2573864) {
                    world.removeBlock(pos, false);
                    world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 4.0f, true, Explosion.DestructionType.BREAK);
                    world.setBlockState(pos, Blocks.LAVA.getDefaultState());
                    FissionRadEntity rad = new FissionRadEntity(ClientMain.REACTOR_RAD_ENTITY, world);
                    rad.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
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
        // Top insert 0 (fuel)
        // Right insert 1
        // Left insert 1

        return switch (localDir) {
            default ->
                    side.getOpposite() == Direction.NORTH && slot == 0 ||
                            side.getOpposite() == Direction.EAST && slot == 0 ||
                            side.getOpposite() == Direction.WEST && slot == 0;
            case EAST ->
                    side.rotateYClockwise() == Direction.NORTH && slot == 0 ||
                            side.rotateYClockwise() == Direction.EAST && slot == 0 ||
                            side.rotateYClockwise() == Direction.WEST && slot == 0;
            case SOUTH ->
                    side == Direction.NORTH && slot == 0 ||
                            side == Direction.EAST && slot == 0 ||
                            side == Direction.WEST && slot == 0;
            case WEST ->
                    side.rotateYCounterclockwise() == Direction.NORTH && slot == 0 ||
                            side.rotateYCounterclockwise() == Direction.EAST && slot == 0 ||
                            side.rotateYCounterclockwise() == Direction.WEST && slot == 0;
        };
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(ModAtomicReactorControllerBlock.FACING);

        if(side == Direction.UP) {
            return false;
        }

        // Down extract 2
        if(side == Direction.DOWN) {
            return slot == 2;
        }

        // bottom extract 2
        // right extract 2
        return switch (localDir) {
            default -> side.getOpposite() == Direction.SOUTH && slot == 2 ||
                    side.getOpposite() == Direction.EAST && slot == 2;
            case EAST -> side.rotateYClockwise() == Direction.SOUTH && slot == 2 ||
                    side.rotateYClockwise() == Direction.EAST && slot == 2;
            case SOUTH -> side == Direction.SOUTH && slot == 2 ||
                    side == Direction.EAST && slot == 2;
            case WEST -> side.rotateYCounterclockwise() == Direction.SOUTH && slot == 2 ||
                    side.rotateYCounterclockwise() == Direction.EAST && slot == 2;
        };
    }

    //End sided inv

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, ItemStack output) {
        return inventory.getStack(2).getItem() == output.getItem() || inventory.getStack(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(2).getMaxCount() > inventory.getStack(2).getCount();
    }

}
