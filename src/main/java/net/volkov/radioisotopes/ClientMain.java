package net.volkov.radioisotopes;

import net.fabricmc.api.ModInitializer;
import net.volkov.radioisotopes.block.ModBlocks;
import net.volkov.radioisotopes.block.entity.ModBlockEntities;
import net.volkov.radioisotopes.effect.ModEffects;
import net.volkov.radioisotopes.entity.ModEntities;
import net.volkov.radioisotopes.item.ModItems;
import net.volkov.radioisotopes.recipe.ModRecipes;
import net.volkov.radioisotopes.screen.ModScreenHandlers;
import net.volkov.radioisotopes.util.ModLootTableModifiers;
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

	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEffects.registerEffects();

		ModRegistries.registerModStuffs();

		ModWorldGen.generateModWorldGen();
		ModLootTableModifiers.modifyLootTables();

		ModBlockEntities.registerAllBlockEntities();
		ModEntities.registerAllEntities();
		ModRecipes.registerRecipes();

		ModScreenHandlers.registerAllScreenHandler();

		ModVillagers.setupPOIs();
		ModStructures.registerStructureFeatures();

	}
}
