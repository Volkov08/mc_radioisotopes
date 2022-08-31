package net.volkov.radioisotopes.util;

import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.item.ModItems;
import net.fabricmc.fabric.api.registry.FuelRegistry;

public class ModRegistries {
    public static void registerModStuffs() {
        registerFuels();
    }

    private static void registerFuels() {
        ClientMain.LOGGER.info("Registering fuels for " + ClientMain.MOD_ID);
        FuelRegistry registry = FuelRegistry.INSTANCE;

        registry.add(ModItems.NUCLEAR_FUEL_ROD, 36000);
    }

}
