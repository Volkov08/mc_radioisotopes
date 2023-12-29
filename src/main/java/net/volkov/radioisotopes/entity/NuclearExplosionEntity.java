package net.volkov.radioisotopes.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class NuclearExplosionEntity extends Entity {
    private int radius;
    private int x_range;
    private int y_range;
    private int z_range;
    private boolean has_rad;
    private int bolt;

    public NuclearExplosionEntity(EntityType<? extends Entity> type, World world, int radius) {
        super(type, world);
        this.radius = radius;
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
                RadEntity rad = new RadEntity(ModEntities.RAD_ENTITY, world, 70000, radius * 1.4d, 7000d);
                rad.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
                world.spawnEntity(rad);
                has_rad = true;
            } else if (bolt < 10) {
                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
                lightning.setPosition(pos.getX(), pos.getY(), pos.getZ());
                world.spawnEntity(lightning);
                bolt += 1;
            }

            Box box = new Box(pos).expand((double) 5 / 3 * radius);
            for (Entity entity : this.world.getOtherEntities(this, box)) {
                if (entity instanceof PlayerEntity || entity instanceof MobEntity) {
                    if (this.getPos().distanceTo(entity.getPos()) <= (double) 5 / 3 * radius) {
                        entity.damage(DamageSource.MAGIC, (float) (18.0d - (this.getPos().distanceTo(entity.getPos()) / ((double) 5 / 3 * radius) * 18.0d)));
                        entity.setOnFireFor(60);
                    }
                }
            }

            int i = 0;
            while (i < 500) {
                if (y_range >= -radius) {
                    if (x_range <= radius) {
                        if (z_range <= radius) {
                            BlockPos dpos = pos.add(x_range, y_range, z_range);
                            if (pos.isWithinDistance(dpos, (double) (radius * 4) / 5)) {
                                this.world.removeBlock(dpos, false);
                            } else if (pos.isWithinDistance(dpos, radius) && this.random.nextInt(2) == 0) {
                                this.world.removeBlock(dpos, false);
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
                }
                i++;
            }
        }
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
        nbt.putBoolean("nuke.has_rad", has_rad);
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
        has_rad = nbt.getBoolean("nuke.has_rad");
        bolt = nbt.getInt("nuke.bolt");
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
