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

public class FusionExplosionEntity extends Entity {

    private int y_1 = -80;
    private int y_2 = -45;
    private int y_3 = -28;
    private int y_4 = -20;
    private int x = 0;
    private boolean bolt = true;



    public FusionExplosionEntity(EntityType<? extends Entity> type, World world) {
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
                if (x <= 360) {
                    int r = 12;
                    world.createExplosion(null, pos.getX() + (r * Math.cos(x)), pos.getY() + y_1, pos.getZ() + (r * Math.sin(x)), 27.0f, false, Explosion.DestructionType.DESTROY);
                    x += 40;
                } else {
                    x = 0;
                    y_1 += 7;
                }
            } else if (y_2 <= 40) {
                if (x <= 300) {
                    int r = 38;
                    world.createExplosion(null, pos.getX() + (r * Math.cos(x)), pos.getY() + y_2, pos.getZ() + (r * Math.sin(x)), 30.0f, false, Explosion.DestructionType.DESTROY);
                    x += 8;
                } else {
                    x = 0;
                    y_2 += 12;
                }
            } else if (y_3 <= 30) {
                if (x <= 350) {
                    int r = 58;
                    world.createExplosion(null, pos.getX() + (r * Math.cos(x)), pos.getY() + y_3, pos.getZ() + (r * Math.sin(x)), 30.0f, false, Explosion.DestructionType.DESTROY);
                    x += 7;
                } else {
                    x = 0;
                    y_3 += 12;
                }
            } else if (y_4 <= 30) {
                if (x <= 350) {
                    int r = 72;
                    world.createExplosion(null, pos.getX() + (r * Math.cos(x)), pos.getY() + y_4, pos.getZ() + (r * Math.sin(x)), 30.0f, false, Explosion.DestructionType.DESTROY);
                    x += 5;
                } else {
                    x = 0;
                    y_4 += 10;
                }
            } else if (x <= 135) {
                int r = 89;
                world.createExplosion(null, pos.getX() + (r * Math.cos(x)), pos.getY(), pos.getZ() + (r * Math.sin(x)), 30.0f, true, Explosion.DestructionType.DESTROY);
                x += 2;
            } else {
                FissionRadEntity rad = new FissionRadEntity(ModEntities.FISSION_RAD_ENTITY, world);
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
