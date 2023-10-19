package net.volkov.radioisotopes.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class FusionExplosionEntity extends Entity {

    private int y_1 = -80;
    private int y_2 = -45;
    private int y_3 = -28;
    private int y_4 = -20;
    private int x = 0;
    private int r = 0;
    private int bolt = 0;
    private boolean has_rad = false;

    public FusionExplosionEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    public void tick() {
        super.tick();
        if (!world.isClient()) {
            Vec3d pos = this.getPos();
            if (!has_rad) {
                RadEntity rad = new RadEntity(ModEntities.RAD_ENTITY, world, 70000, 95d, 7000d);
                rad.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
                world.spawnEntity(rad);
                has_rad = true;
            } else if (bolt < 10) {
                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
                lightning.setPosition(pos.getX(), pos.getY(), pos.getZ());
                world.spawnEntity(lightning);
                bolt += 1;
            } else if (y_1 <= 60) {
                if (x <= 360) {
                    r = 14;
                    world.createExplosion(null, pos.getX() + (r * Math.sin(Math.toRadians(x))), pos.getY() + y_1, pos.getZ() + (r * Math.cos(Math.toRadians(x))), 36.0f, false, Explosion.DestructionType.DESTROY);
                    x += 32;
                } else {
                    x = 0;
                    y_1 += 7;
                }
            } else if (y_2 <= 40) {
                if (x <= 360) {
                    r = 38;
                    world.createExplosion(null, pos.getX() + (r * Math.sin(Math.toRadians(x))), pos.getY() + y_2, pos.getZ() + (r * Math.cos(Math.toRadians(x))), 36.0f, false, Explosion.DestructionType.DESTROY);
                    x += 10;
                } else {
                    x = 0;
                    y_2 += 12;
                }
            } else if (y_3 <= 30) {
                if (x <= 360) {
                    r = 58;
                    world.createExplosion(null, pos.getX() + (r * Math.sin(Math.toRadians(x))), pos.getY() + y_3, pos.getZ() + (r * Math.cos(Math.toRadians(x))), 36.0f, false, Explosion.DestructionType.DESTROY);
                    x += 6;
                } else {
                    x = 0;
                    y_3 += 12;
                }
            } else if (y_4 <= 30) {
                if (x <= 360) {
                    r = 72;
                    world.createExplosion(null, pos.getX() + (r * Math.sin(Math.toRadians(x))), pos.getY() + y_4, pos.getZ() + (r * Math.cos(Math.toRadians(x))), 36.0f, false, Explosion.DestructionType.DESTROY);
                    x += 4;
                } else {
                    x = 0;
                    y_4 += 10;
                }
            } else if (x <= 360) {
                r = 91;
                world.createExplosion(null, pos.getX() + (r * Math.sin(Math.toRadians(x))), pos.getY() + 4, pos.getZ() + (r * Math.cos(Math.toRadians(x))), 27.0f, true, Explosion.DestructionType.DESTROY);
                x += 4;
            } else {
                MinecraftServer server = world.getServer();
                if (server != null) {
                    ServerWorld serverWorld = server.getWorld(world.getRegistryKey());
                    serverWorld.setWeather(0, 3600, true, true);
                }
                remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putBoolean("fusion_explosion.has_rad", has_rad);
        nbt.putInt("fusion_explosion.bolt", bolt);
        nbt.putInt("fusion_explosion.y_1", y_1);
        nbt.putInt("fusion_explosion.y_2", y_2);
        nbt.putInt("fusion_explosion.y_3", y_3);
        nbt.putInt("fusion_explosion.y_4", y_4);
        nbt.putInt("fusion_explosion.x", x);
        nbt.putInt("fusion_explosion.r", r);
        return super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        has_rad = nbt.getBoolean("fusion_explosion.has_rad");
        bolt = nbt.getInt("fusion_explosion.bolt");
        y_1 = nbt.getInt("fusion_explosion.y_1");
        y_2 = nbt.getInt("fusion_explosion.y_2");
        y_3 = nbt.getInt("fusion_explosion.y_3");
        y_4 = nbt.getInt("fusion_explosion.y_4");
        x = nbt.getInt("fusion_explosion.x");
        r = nbt.getInt("fusion_explosion.r");
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
