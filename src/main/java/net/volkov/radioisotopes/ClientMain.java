package net.volkov.radioisotopes;

import net.fabricmc.api.ModInitializer;
import net.volkov.radioisotopes.block.ModBlocks;
import net.volkov.radioisotopes.item.ModItems;
import net.volkov.radioisotopes.util.ModRegistries;
import net.volkov.radioisotopes.world.gen.ModWorldGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientMain implements ModInitializer {
	public static final String MOD_ID = "radioisotopes";
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModRegistries.registerModStuffs();
		ModWorldGen.generateModWorldGen();
	}
}
