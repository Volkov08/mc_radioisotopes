package net.volkov.radioisotopes.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.volkov.radioisotopes.ClientMain;

public class FissionExplosionEntity extends Entity {

    private int y_1 = -50;
    private int y_2 = -25;
    private int x = 0;
    private boolean bolt = true;


    public FissionExplosionEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    public void tick() {
        super.tick();
        if (!world.isClient) {
            Vec3d pos = this.getPos();
            if (bolt) {
                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
                lightning.setPosition(pos.getX(), pos.getY(), pos.getZ());
                world.spawnEntity(lightning);
                bolt = false;
            } else if (y_1 <= 60) {
                if (x <= 275) {
                    int r = 5;
                    world.createExplosion(null, pos.getX() + (r * Math.cos(x)), pos.getY() + y_1, pos.getZ() + (r * Math.sin(x)), 25.0f, false, Explosion.DestructionType.DESTROY);
                    x += 55;
                } else {
                    x = 0;
                    y_1 += 7;
                }
            } else if (y_2 <= 40) {
                if (x <= 250) {
                    int r = 30;
                    world.createExplosion(null, pos.getX() + (r * Math.cos(x)), pos.getY() + y_2, pos.getZ() + (r * Math.sin(x)), 30.0f, false, Explosion.DestructionType.DESTROY);
                    x += 8;
                } else {
                    x = 0;
                    y_2 += 12;
                }
            } else if (x <= 135) {
                int r = 55;
                world.createExplosion(null, pos.getX() + (r * Math.cos(x)), pos.getY(), pos.getZ() + (r * Math.sin(x)), 24.0f, true, Explosion.DestructionType.DESTROY);
                x += 6;
            } else {
                FissionRadEntity rad = new FissionRadEntity(ClientMain.FISSION_RAD_ENTITY, world);
                rad.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
                world.spawnEntity(rad);
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
