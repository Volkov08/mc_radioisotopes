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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.volkov.radioisotopes.effect.ModEffects;
import net.volkov.radioisotopes.item.ModArmorMaterials;

public class FissionRadEntity extends Entity {

    private int tickCounter = 0;
    private final double full_lifetime = 90000;
    private int lifetime = 90000;
    private final float EFFECT_RANGE_1 = 30.0f;
    private final float EFFECT_RANGE_2 = 70.0f;
    private final float EFFECT_RANGE_3 = 110.0f;

    private final double dur_1 = 70000;
    private final double dur_2 = 42000;
    private final double dur_3 = 15000;

    public FissionRadEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    public void tick() {
        super.tick();

        tickCounter++;
        if (tickCounter < 20) {
            return; // skip tick checks until tickCounter reaches 10
        } else {
            tickCounter = 0; // reset tickCounter to 0
        }

        if (lifetime > 0) {
            lifetime--;
        } else {
            remove(RemovalReason.DISCARDED); // Despawn entity when lifetime reaches 0
        }

        Vec3d entityPos = getPos();
        for (PlayerEntity player : world.getPlayers()) {
            Vec3d playerPos = player.getPos();
            double distance = entityPos.distanceTo(playerPos);

            if (distance <= EFFECT_RANGE_1) {
                applyEffect(player, dur_1, 40000);
            }
            else if (distance <= EFFECT_RANGE_2) {
                applyEffect(player, dur_2, 40000);
            }
            else if (distance <= EFFECT_RANGE_3) {
                applyEffect(player, dur_3, 40000);
            }

        }
    }

    public boolean hasArmorOn(PlayerEntity player, ArmorMaterial material) {
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

    public void applyEffect(PlayerEntity player, double dur, int div) {
        int r_dur = (int) Math.round((double) lifetime / full_lifetime * dur);
        if (!player.hasStatusEffect(ModEffects.RAD_POISON)) {
            if (r_dur >= div) {
                if (!hasArmorOn(player, ModArmorMaterials.HEAVY_LEAD)) {
                    player.addStatusEffect(new StatusEffectInstance(ModEffects.RAD_POISON, r_dur, 0));
                }
            }
            else {
                if (!hasArmorOn(player, ModArmorMaterials.LEAD) && !hasArmorOn(player, ModArmorMaterials.HEAVY_LEAD)) {
                    player.addStatusEffect(new StatusEffectInstance(ModEffects.RAD_POISON, r_dur, 0));
                }
            }

        }
        else if (player.getStatusEffect(ModEffects.RAD_POISON).getDuration() < r_dur) {
            if (r_dur >= div) {
                if (!hasArmorOn(player, ModArmorMaterials.HEAVY_LEAD)) {
                    player.addStatusEffect(new StatusEffectInstance(ModEffects.RAD_POISON, r_dur, 0));
                }
            }
            else {
                if (!hasArmorOn(player, ModArmorMaterials.LEAD) && !hasArmorOn(player, ModArmorMaterials.HEAVY_LEAD)) {
                    player.addStatusEffect(new StatusEffectInstance(ModEffects.RAD_POISON, r_dur, 0));
                }
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
