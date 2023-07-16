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

public class FissionExplosionEntity extends Entity {

    private int y_1 = -52;
    private int y_2 = -25;
    private int x = 0;
    private int bolt = 0;

    public FissionExplosionEntity(EntityType<? extends Entity> type, World world) {
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
            if (bolt < 10) {
                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
                lightning.setPosition(pos.getX(), pos.getY(), pos.getZ());
                world.spawnEntity(lightning);
                bolt += 1;
            } else if (y_1 <= 60) {
                if (x <= 360) {
                    int r = 3;
                    world.createExplosion(null, pos.getX() + (r * Math.sin(Math.toRadians(x))), pos.getY() + y_1, pos.getZ() + (r * Math.cos(Math.toRadians(x))), 18.0f, false, Explosion.DestructionType.DESTROY);
                    x += 90;
                } else {
                    x = 0;
                    y_1 += 7;
                }
            } else if (y_2 <= 40) {
                if (x <= 360) {
                    int r = 14;
                    world.createExplosion(null, pos.getX() + (r * Math.sin(Math.toRadians(x))), pos.getY() + y_2, pos.getZ() + (r * Math.cos(Math.toRadians(x))), 30.0f, false, Explosion.DestructionType.DESTROY);
                    x += 24;
                } else {
                    x = 0;
                    y_2 += 12;
                }
            } else if (x <= 360) {
                int r = 35;
                world.createExplosion(null, pos.getX() + (r * Math.sin(Math.toRadians(x))), pos.getY() + 4, pos.getZ() + (r * Math.cos(Math.toRadians(x))), 27.0f, true, Explosion.DestructionType.DESTROY);
                x += 15;
            } else {
                FissionRadEntity rad = new FissionRadEntity(ModEntities.FISSION_RAD_ENTITY, world);
                rad.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
                world.spawnEntity(rad);
                MinecraftServer server = world.getServer();
                if (server != null) {
                    ServerWorld serverWorld = server.getWorld(world.getRegistryKey());
                    serverWorld.setTimeOfDay(13000);
                    serverWorld.setWeather(0, 3600, true, true);
                }
                remove(RemovalReason.DISCARDED);
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
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
