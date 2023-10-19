package net.volkov.radioisotopes.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class M388ExplosionEntity extends Entity {
    private int bolt = 0;
    private int y_1 = -15;
    private int x = 0;
    private int r = 0;
    private boolean has_rad = false;

    public M388ExplosionEntity(EntityType<?> type, World world) {
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
                RadEntity rad = new RadEntity(ModEntities.RAD_ENTITY, world, 70000, 43d, 7000d);
                rad.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
                world.spawnEntity(rad);
                has_rad = true;
            } else if (bolt < 10) {
                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
                lightning.setPosition(pos.getX(), pos.getY(), pos.getZ());
                world.spawnEntity(lightning);
                bolt += 1;
            } else if (y_1 <= 25) {
                if (x <= 360) {
                    r = 4;
                    world.createExplosion(null, pos.getX() + (r * Math.sin(Math.toRadians(x))), pos.getY() + y_1, pos.getZ() + (r * Math.cos(Math.toRadians(x))), 12.0f, true, Explosion.DestructionType.DESTROY);
                    x += 45;
                } else {
                    x = 0;
                    y_1 += 5;
                }
            } else if (x <= 360) {
                r = 15;
                world.createExplosion(null, pos.getX() + (r * Math.sin(Math.toRadians(x))), pos.getY(), pos.getZ() + (r * Math.cos(Math.toRadians(x))), 10.0f, true, Explosion.DestructionType.DESTROY);
                x += 30;
            } else {
                remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putBoolean("m388_explosion.has_rad", has_rad);
        nbt.putInt("m388_explosion.y_1", y_1);
        nbt.putInt("m388_explosion.x", x);
        nbt.putInt("m388_explosion.bolt", bolt);
        return super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        y_1 = nbt.getInt("m388_explosion.y_1");
        x = nbt.getInt("m388_explosion.x");
        bolt = nbt.getInt("m388_explosion.bolt");
        has_rad = nbt.getBoolean("m388_explosion.has_rad");
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
