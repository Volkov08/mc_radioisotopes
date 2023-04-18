package net.volkov.radioisotopes.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.volkov.radioisotopes.block.ModBlocks;
import net.volkov.radioisotopes.effect.ModEffects;
import net.volkov.radioisotopes.item.ModArmorMaterials;

public class FissionRadEntity extends Entity {

    private int tickCounter = 0;
    private final double full_lifetime = 70000d;
    private int lifetime = 70000;

    public FissionRadEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
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

            for (PlayerEntity player : world.getPlayers()) {
                protCheck(player, 95d);
            }
        }
    }

    private boolean hasArmorOn(PlayerEntity player, ArmorMaterial material) {
        int leadArmorCount = 0;
        for (ItemStack armorItem : player.getArmorItems()) {
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

    private void applyEffect(PlayerEntity player, double dur, double distance, double full_distance, double div) {
        double r_dur = ((double) lifetime) / full_lifetime * dur;
        double f_dur = r_dur - (distance * r_dur / full_distance);
        if (f_dur > 0.0d) {
            if (!player.hasStatusEffect(ModEffects.RAD_POISON)) {
                if (f_dur >= div) {
                    if (!hasArmorOn(player, ModArmorMaterials.HEAVY_LEAD)) {
                        if (hasArmorOn(player, ModArmorMaterials.LEAD)) {
                            player.addStatusEffect(new StatusEffectInstance(ModEffects.RAD_POISON, (int) Math.round(f_dur / 2), 0));
                        }
                        else {
                            player.addStatusEffect(new StatusEffectInstance(ModEffects.RAD_POISON, (int) Math.round(f_dur), 0));
                        }
                    }
                } else {
                    if (!hasArmorOn(player, ModArmorMaterials.LEAD) && !hasArmorOn(player, ModArmorMaterials.HEAVY_LEAD)) {
                        player.addStatusEffect(new StatusEffectInstance(ModEffects.RAD_POISON, (int) Math.round(f_dur), 0));
                    }
                }

            } else if (player.getStatusEffect(ModEffects.RAD_POISON).getDuration() < f_dur) {
                if (f_dur >= div) {
                    if (!hasArmorOn(player, ModArmorMaterials.HEAVY_LEAD)) {
                        if (hasArmorOn(player, ModArmorMaterials.LEAD)) {
                            player.addStatusEffect(new StatusEffectInstance(ModEffects.RAD_POISON, (int) Math.round(f_dur / 2), 0));
                        }
                        else {
                            player.addStatusEffect(new StatusEffectInstance(ModEffects.RAD_POISON, (int) Math.round(f_dur), 0));
                        }

                    }
                } else {
                    if (!hasArmorOn(player, ModArmorMaterials.LEAD) && !hasArmorOn(player, ModArmorMaterials.HEAVY_LEAD)) {
                        player.addStatusEffect(new StatusEffectInstance(ModEffects.RAD_POISON, (int) Math.round(f_dur), 0));
                    }
                }
            }
        }
    }

    private void protCheck(PlayerEntity player, double c_distance) {
        boolean isProt = false;
        Vec3d entityPos = this.getPos();
        Vec3d playerPos = player.getPos().add(0, player.getEyeHeight(player.getPose()), 0);
        Vec3d rayDir = playerPos.subtract(entityPos).normalize();
        double maxDistance = entityPos.distanceTo(playerPos);
        if (maxDistance < c_distance) {
            BlockPos.Mutable pos = new BlockPos.Mutable();
            for (double distance = 0; distance < maxDistance; distance += 0.1) {
                pos.set(entityPos.add(rayDir.multiply(distance)).getX(), entityPos.add(rayDir.multiply(distance)).getY(), entityPos.add(rayDir.multiply(distance)).getZ());
                if (world.getBlockState(pos).getBlock() == ModBlocks.LEAD_BLOCK || world.getBlockState(pos).getBlock() == ModBlocks.INDUSTRIAL_CASING || world.getBlockState(pos).getBlock() == ModBlocks.LEAD_WALL) {
                    isProt = true;
                    break;
                }
            }
            if (!isProt) {
                applyEffect(player, 52000d, maxDistance, c_distance, 30000d);
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
