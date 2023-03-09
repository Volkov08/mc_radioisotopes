package net.volkov.radioisotopes.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.volkov.radioisotopes.ClientMain;

public class ModEntities {

    public static EntityType<FissionRadEntity> FISSION_RAD_ENTITY;
    public static EntityType<ReactorRadEntity> REACTOR_RAD_ENTITY;
    public static EntityType<FissionExplosionEntity> FISSION_EXPLOSION_ENTITY;
    public static EntityType<FusionExplosionEntity> FUSION_EXPLOSION_ENTITY;

    public static void registerAllEntities() {
        FISSION_RAD_ENTITY = Registry.register(
                Registry.ENTITY_TYPE,
                new Identifier(ClientMain.MOD_ID, "fission_rad_entity"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, FissionRadEntity::new)
                        .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                        .build()
        );
        REACTOR_RAD_ENTITY = Registry.register(
                Registry.ENTITY_TYPE,
                new Identifier(ClientMain.MOD_ID, "reactor_rad_entity"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, ReactorRadEntity::new)
                        .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                        .build()
        );
        FISSION_EXPLOSION_ENTITY = Registry.register(
                Registry.ENTITY_TYPE,
                new Identifier(ClientMain.MOD_ID, "fission_explosion_entity"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, FissionExplosionEntity::new)
                        .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                        .build()
        );
        FUSION_EXPLOSION_ENTITY = Registry.register(
                Registry.ENTITY_TYPE,
                new Identifier(ClientMain.MOD_ID, "fusion_explosion_entity"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, FusionExplosionEntity::new)
                        .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                        .build()
        );
    }





}
