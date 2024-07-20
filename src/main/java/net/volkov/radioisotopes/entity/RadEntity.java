package net.volkov.radioisotopes.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.volkov.radioisotopes.block.ModBlocks;
import net.volkov.radioisotopes.effect.ModEffects;
import net.volkov.radioisotopes.item.ModArmorMaterials;

public class RadEntity extends Entity {

    private int tickCounter = 0;
    private int lifetime;
    private double full_lifetime;
    public double distance;
    private double strength;
    private boolean canShield;

    public RadEntity(EntityType<? extends Entity> entityType, World world, int lifetime, double distance, double strength, boolean canShield) {
        super(entityType, world);
        this.lifetime = lifetime;
        this.full_lifetime = lifetime;
        this.distance = distance;
        this.strength = strength;
        this.canShield = canShield;
    }

    public RadEntity(EntityType<RadEntity> radEntityType, World world) {
        super(radEntityType, world);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    public void tick() {
        super.tick();
        if (!world.isClient()) {
            if (lifetime > 0) {
                lifetime--;
            } else {
                remove(RemovalReason.DISCARDED); // Despawn entity when lifetime reaches 0
            }

            tickCounter++;
            if (tickCounter < 100) {
                return; // skip tick checks until tickCounter reaches 100
            } else {
                tickCounter = 0; // reset tickCounter to 0
            }
            for (LivingEntity entity : world.getEntitiesByClass(LivingEntity.class, new Box(this.getBlockPos()).expand(distance), entity -> true)) {
                protCheck(entity);
            }
        }
    }

    private boolean hasArmorOn(LivingEntity entity, ArmorMaterial material) {
        if (entity.isPlayer()) {
            int leadArmorCount = 0;
            for (ItemStack armorItem : entity.getArmorItems()) {
                Item item = armorItem.getItem();
                if (item instanceof ArmorItem && ((ArmorItem) item).getMaterial() == material) {
                    EquipmentSlot slotType = ((ArmorItem) item).getSlotType();
                    if (slotType == EquipmentSlot.HEAD || slotType == EquipmentSlot.CHEST
                            || slotType == EquipmentSlot.LEGS || slotType == EquipmentSlot.FEET) {
                        leadArmorCount++;
                    }
                }
            }
            boolean hasLeadArmor = leadArmorCount == 4;
            return hasLeadArmor;
        }
        return false;
    }

    private void applyEffect(LivingEntity entity, double max_distance, double div) {
        double r_dur = ((double) lifetime) / full_lifetime * strength;
        double f_dur = r_dur - (max_distance * r_dur / distance);
        if (f_dur >= 200.0d) {
            if (!entity.hasStatusEffect(ModEffects.RAD_POISON)) {
                if (f_dur >= div) {
                    if (!hasArmorOn(entity, ModArmorMaterials.HEAVY_LEAD)) {
                        if (hasArmorOn(entity, ModArmorMaterials.LEAD)) {
                            entity.addStatusEffect(new StatusEffectInstance(ModEffects.RAD_POISON, (int) Math.round(f_dur / 2), 0));
                        }
                        else {
                            entity.addStatusEffect(new StatusEffectInstance(ModEffects.RAD_POISON, (int) Math.round(f_dur), 0));
                        }
                    }
                } else {
                    if (!hasArmorOn(entity, ModArmorMaterials.LEAD) && !hasArmorOn(entity, ModArmorMaterials.HEAVY_LEAD)) {
                        entity.addStatusEffect(new StatusEffectInstance(ModEffects.RAD_POISON, (int) Math.round(f_dur), 0));
                    }
                }
            } else if (entity.getStatusEffect(ModEffects.RAD_POISON).getDuration() < 40000) {
                if (f_dur >= div) {
                    if (!hasArmorOn(entity, ModArmorMaterials.HEAVY_LEAD)) {
                        if (hasArmorOn(entity, ModArmorMaterials.LEAD)) {
                            entity.addStatusEffect(new StatusEffectInstance(ModEffects.RAD_POISON, Math.min((int)(entity.getStatusEffect(ModEffects.RAD_POISON).getDuration() + Math.round(f_dur / 2)), 40000), 0));
                        }
                        else {
                            entity.addStatusEffect(new StatusEffectInstance(ModEffects.RAD_POISON, Math.min((int)(entity.getStatusEffect(ModEffects.RAD_POISON).getDuration() + Math.round(f_dur)), 40000), 0));
                        }
                    }
                } else {
                    if (!hasArmorOn(entity, ModArmorMaterials.LEAD) && !hasArmorOn(entity, ModArmorMaterials.HEAVY_LEAD)) {
                        entity.addStatusEffect(new StatusEffectInstance(ModEffects.RAD_POISON, Math.min((int)(entity.getStatusEffect(ModEffects.RAD_POISON).getDuration() + Math.round(f_dur)), 40000), 0));
                    }
                }
            }
        }
    }

    private void protCheck(LivingEntity entity) {
        Vec3d entityPos = this.getPos();
        Vec3d playerPos = entity.getPos().add(0d, entity.getEyeHeight(entity.getPose()), 0d);
        double maxDistance = entityPos.distanceTo(playerPos);
        if (maxDistance <= this.distance) {
            if (canShield) {
                Vec3d rayDir = playerPos.subtract(entityPos).normalize();
                BlockPos.Mutable pos = new BlockPos.Mutable();
                for (double ray_distance = 0d; ray_distance < maxDistance; ray_distance += 0.08d) {
                    pos.set(entityPos.add(rayDir.multiply(ray_distance)).getX(), entityPos.add(rayDir.multiply(ray_distance)).getY(), entityPos.add(rayDir.multiply(ray_distance)).getZ());
                    if (world.getBlockState(pos).getBlock() == ModBlocks.LEAD_BLOCK || world.getBlockState(pos).getBlock() == ModBlocks.INDUSTRIAL_CASING || world.getBlockState(pos).getBlock() == ModBlocks.LEAD_WALL) {
                        return;
                    }
                }
            }
        } else {
            return;
        }
        applyEffect(entity, maxDistance, 3200d);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt("rad.lifetime", lifetime);
        nbt.putDouble("rad.full_lifetime", full_lifetime);
        nbt.putDouble("rad.distance", distance);
        nbt.putDouble("rad.strength", strength);
        nbt.putBoolean("rad.canShield", canShield);
        return super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        lifetime = nbt.getInt("rad.lifetime");
        full_lifetime = nbt.getDouble("rad.full_lifetime");
        distance = nbt.getDouble("rad.distance");
        strength = nbt.getDouble("rad.strength");
        canShield = nbt.getBoolean("rad.canShield");
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

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
