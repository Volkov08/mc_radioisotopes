package net.volkov.radioisotopes.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.block.ModBlocks;

public class ModBlockEntities {
    public static BlockEntityType<DeuteriumGeneratorBlockEntity> DEUTERIUM_GENERATOR;
    public static BlockEntityType<UraniumCentrifugeBlockEntity> URANIUM_CENTRIFUGE;
    public static BlockEntityType<AtomicReactorControllerBlockEntity> ATOMIC_REACTOR_CONTROLLER;

    public static void registerAllBlockEntities() {
        ClientMain.LOGGER.info("Registering mod block entities for " + ClientMain.MOD_ID);
        DEUTERIUM_GENERATOR = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(ClientMain.MOD_ID, "deuterium_generator"),
                FabricBlockEntityTypeBuilder.create(DeuteriumGeneratorBlockEntity::new,
                        ModBlocks.DEUTERIUM_GENERATOR).build(null));
        URANIUM_CENTRIFUGE = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(ClientMain.MOD_ID, "uranium_centrifuge"),
                FabricBlockEntityTypeBuilder.create(UraniumCentrifugeBlockEntity::new,
                        ModBlocks.URANIUM_CENTRIFUGE).build(null));
        ATOMIC_REACTOR_CONTROLLER = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(ClientMain.MOD_ID, "atomic_reactor_controller"),
                FabricBlockEntityTypeBuilder.create(AtomicReactorControllerBlockEntity::new,
                        ModBlocks.ATOMIC_REACTOR_CONTROLLER).build(null));
    }

}
