package net.volkov.radioisotopes.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class NuclearExplosionEntity extends Entity {
    private int radius;
    private int x_range;
    private int y_range;
    private int z_range;
    private boolean has_rad;
    private boolean has_damaged;
    private int bolt;
    private double radiation;

    public NuclearExplosionEntity(EntityType<? extends Entity> type, World world, int radius, double radiation) {
        super(type, world);
        this.radius = radius;
        this.radiation = radiation;
        this.x_range = -radius;
        this.y_range = radius;
        this.z_range = -radius;
    }

    public NuclearExplosionEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    public void tick() {
        super.tick();
        if (!world.isClient()) {
            BlockPos pos = this.getBlockPos();
            if (!has_rad) {
                RadEntity rad = new RadEntity(ModEntities.RAD_ENTITY, world, radius * 1.5d, radiation, false);
                rad.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
                world.spawnEntity(rad);
                has_rad = true;
            } else if (bolt < 10) {
                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
                lightning.setPosition(pos.getX(), pos.getY(), pos.getZ());
                world.spawnEntity(lightning);
                bolt += 1;
            }

            if(!has_damaged) {
                Box box = new Box(pos).expand(1.2d * radius);
                for (Entity entity : this.world.getOtherEntities(this, box)) {
                    if (entity instanceof PlayerEntity || entity instanceof MobEntity) {
                        if (this.getPos().distanceTo(entity.getPos()) <= 0.64d * radius) {
                            entity.damage(DamageSource.GENERIC, 6000.0f);
                        } else if (this.getPos().distanceTo(entity.getPos()) <= 1.2d * radius) {
                            entity.damage(DamageSource.GENERIC, 6000.0f - ((float) this.getPos().distanceTo(entity.getPos()) - (0.64f * radius)) / (0.56f * radius) * 6000.0f);
                        }
                        entity.setOnFireFor(60);
                    }
                }
                has_damaged = true;
            }

            int i = 0;
            while (i < 150) {
                if (y_range >= -radius) {
                    if (x_range <= radius) {
                        if (z_range <= radius) {
                            BlockPos dpos = pos.add(x_range, y_range, z_range);
                            BlockState block = world.getBlockState(dpos);
                            if (pos.isWithinDistance(dpos, radius) && !block.isAir()) {
                                if (pos.isWithinDistance(dpos, radius * 0.64d)) {
                                    world.setBlockState(dpos, Blocks.AIR.getDefaultState());
                                    i++;
                                } else if (pos.isWithinDistance(dpos, radius * 0.76d)) {
                                    if (isWeakBlock(block)) {
                                        world.setBlockState(dpos, Blocks.AIR.getDefaultState());
                                        i++;
                                    } else if (block.isIn(BlockTags.ICE)) {
                                        world.setBlockState(dpos, Blocks.WATER.getDefaultState());
                                        i++;
                                    } else if (random.nextInt(3) > 0) {
                                        world.setBlockState(dpos, Blocks.AIR.getDefaultState());
                                        i++;
                                    } else if (block.isOf(Blocks.GRASS_BLOCK)) {
                                        world.setBlockState(dpos, Blocks.DIRT.getDefaultState());
                                        i++;
                                    }
                                } else {
                                    if (block.isIn(BlockTags.ICE)) {
                                        world.setBlockState(dpos, Blocks.WATER.getDefaultState());
                                        i++;
                                    } else if (isWeakBlock(block) && random.nextInt(5) > 0 || block.isIn(BlockTags.SNOW)) {
                                        world.setBlockState(dpos, Blocks.AIR.getDefaultState());
                                        i++;
                                    }
                                }
                            }
                            z_range++;
                        } else {
                            x_range++;
                            z_range = -radius;
                        }
                    } else {
                        y_range--;
                        x_range = -radius;
                    }
                } else {
                    if (radius > 25) {
                        MinecraftServer server = world.getServer();
                        if (server != null) {
                            ServerWorld serverWorld = server.getWorld(world.getRegistryKey());
                            serverWorld.setWeather(0, 3600, true, true);
                        }
                    }
                    remove(RemovalReason.DISCARDED);
                    break;
                }
            }
        }
    }

    private boolean isWeakBlock(BlockState block) {
        return block.isIn(BlockTags.SNOW) || block.isIn(BlockTags.SAND) || block.isOf(Blocks.WATER) || block.isIn(BlockTags.LEAVES) || block.isIn(BlockTags.FLOWERS) || block.isOf(Blocks.GRASS) || block.isOf(Blocks.TALL_GRASS);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt("nuke.radius", radius);
        nbt.putInt("nuke.x_range", x_range);
        nbt.putInt("nuke.y_range", y_range);
        nbt.putInt("nuke.z_range", z_range);
        nbt.putDouble("nuke.radiation", radiation);
        nbt.putBoolean("nuke.has_rad", has_rad);
        nbt.putBoolean("nuke.has_damaged", has_damaged);
        nbt.putInt("nuke.bolt", bolt);
        return super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        radius = nbt.getInt("nuke.radius");
        x_range = nbt.getInt("nuke.x_range");
        y_range = nbt.getInt("nuke.y_range");
        z_range = nbt.getInt("nuke.z_range");
        radiation = nbt.getDouble("nuke.radiation");
        has_rad = nbt.getBoolean("nuke.has_rad");
        has_damaged = nbt.getBoolean("nuke.has_damaged");
        bolt = nbt.getInt("nuke.bolt");
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}