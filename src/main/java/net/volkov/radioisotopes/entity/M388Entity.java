package net.volkov.radioisotopes.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class M388Entity extends PersistentProjectileEntity {
    public M388Entity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
    public M388Entity(World world, double x, double y, double z) {
        super(ModEntities.M388_ENTITY, x, y, z, world);
    }
    public M388Entity(World world, LivingEntity owner) {
        super(ModEntities.M388_ENTITY, owner, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (world.isClient()) {
            this.spawnParticles(4);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.explode();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        this.explode();
    }

    private void spawnParticles(int amount) {
        for (int i = 0; i < amount; ++i) {
            this.world.addParticle(ParticleTypes.SMOKE, this.getParticleX(1.0d), this.getRandomBodyY(), this.getParticleZ(1.0d), 0.0d, 0.0d, 0.0d);
        }
    }

    private void explode() {
        Vec3d pos = this.getPos();
        NuclearExplosionEntity nuke = new NuclearExplosionEntity(ModEntities.NUCLEAR_EXPLOSION_ENTITY, world, 25, 2800d);
        nuke.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
        world.spawnEntity(nuke);
        this.remove(RemovalReason.DISCARDED);
    }
    @Override
    protected ItemStack asItemStack() {
        return null;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
