package net.volkov.radioisotopes.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.block.ModBlocks;

public class ModBlockEntities {
    public static BlockEntityType<DeuteriumGeneratorBlockEntity> DEUTERIUM_GENERATOR;

    public static void registerAllBlockEntities() {
        DEUTERIUM_GENERATOR = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(ClientMain.MOD_ID, "deuterium_generator"),
                FabricBlockEntityTypeBuilder.create(DeuteriumGeneratorBlockEntity::new,
                        ModBlocks.DEUTERIUM_GENERATOR).build(null));
    }

}
