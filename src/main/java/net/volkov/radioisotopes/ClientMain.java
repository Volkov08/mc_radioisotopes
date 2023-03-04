package net.volkov.radioisotopes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.volkov.radioisotopes.block.ModBlocks;
import net.volkov.radioisotopes.block.entity.ModBlockEntities;
import net.volkov.radioisotopes.effect.ModEffects;
import net.volkov.radioisotopes.entity.FissionExplosionEntity;
import net.volkov.radioisotopes.entity.FissionRadEntity;
import net.volkov.radioisotopes.entity.FusionExplosionEntity;
import net.volkov.radioisotopes.entity.ReactorRadEntity;
import net.volkov.radioisotopes.item.ModItems;
import net.volkov.radioisotopes.recipe.ModRecipes;
import net.volkov.radioisotopes.screen.ModScreenHandlers;
import net.volkov.radioisotopes.util.ModRegistries;
import net.volkov.radioisotopes.villager.ModVillagers;
import net.volkov.radioisotopes.world.gen.ModWorldGen;
import net.volkov.radioisotopes.world.structure.ModStructures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientMain implements ModInitializer {
	public static final String MOD_ID = "radioisotopes";
	// This logger is used to write text to the console and the log file.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Registering Entities
	public static final EntityType<FissionRadEntity> FISSION_RAD_ENTITY = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(MOD_ID, "fission_rad_entity"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, FissionRadEntity::new)
					.dimensions(EntityDimensions.fixed(0.5f, 0.5f))
					.build()
	);

	public static final EntityType<ReactorRadEntity> REACTOR_RAD_ENTITY = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(MOD_ID, "reactor_rad_entity"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, ReactorRadEntity::new)
					.dimensions(EntityDimensions.fixed(0.5f, 0.5f))
					.build()
	);
	public static final EntityType<FissionExplosionEntity> FISSION_EXPLOSION_ENTITY = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(MOD_ID, "fission_explosion_entity"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, FissionExplosionEntity::new)
					.dimensions(EntityDimensions.fixed(0.5f, 0.5f))
					.build()
	);
	public static final EntityType<FusionExplosionEntity> FUSION_EXPLOSION_ENTITY = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(MOD_ID, "fusion_explosion_entity"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, FusionExplosionEntity::new)
					.dimensions(EntityDimensions.fixed(0.5f, 0.5f))
					.build()
	);
	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEffects.registerEffects();

		ModRegistries.registerModStuffs();

		ModWorldGen.generateModWorldGen();

		ModBlockEntities.registerAllBlockEntities();
		ModRecipes.registerRecipes();

		ModScreenHandlers.registerAllScreenHandler();

		ModVillagers.setupPOIs();
		ModStructures.registerStructureFeatures();

	}
}
