package net.volkov.radioisotopes.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.volkov.radioisotopes.ClientMain;

public class ModEntities {

    public static EntityType<RadEntity> RAD_ENTITY;
    public static EntityType<NuclearExplosionEntity> NUCLEAR_EXPLOSION_ENTITY;
    public static EntityType<M388Entity> M388_ENTITY;

    public static void registerAllEntities() {
        ClientMain.LOGGER.info("Registering mod entities for " + ClientMain.MOD_ID);
        RAD_ENTITY = Registry.register(
                Registry.ENTITY_TYPE,
                new Identifier(ClientMain.MOD_ID, "rad_entity"),
                FabricEntityTypeBuilder.<RadEntity>create(SpawnGroup.MISC, RadEntity::new)
                        .dimensions(EntityDimensions.fixed(0.1f, 0.1f))
                        .build()
        );
        NUCLEAR_EXPLOSION_ENTITY = Registry.register(
                Registry.ENTITY_TYPE,
                new Identifier(ClientMain.MOD_ID, "nuclear_explosion_entity"),
                FabricEntityTypeBuilder.<NuclearExplosionEntity>create(SpawnGroup.MISC, NuclearExplosionEntity::new)
                        .dimensions(EntityDimensions.fixed(0.1f, 0.1f))
                        .build()
        );
        M388_ENTITY = Registry.register(
                Registry.ENTITY_TYPE,
                new Identifier(ClientMain.MOD_ID, "m388_entity"),
                FabricEntityTypeBuilder.<M388Entity>create(SpawnGroup.MISC, M388Entity::new)
                        .dimensions(EntityDimensions.fixed(0.4f, 0.4f))
                        .build()
        );
    }





}
