package net.volkov.radioisotopes.world.feature;


import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.block.ModBlocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;

import java.util.List;


public class ModConfiguredFeatures {

   public static final List<OreFeatureConfig.Target> OVERWORLD_URANIUM_ORES = List.of(
           OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
                   ModBlocks.URANIUM_ORE.getDefaultState()),
           OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES,
                   ModBlocks.DEEPSLATE_URANIUM_ORE.getDefaultState()));

   public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> URANIUM_ORE =
           ConfiguredFeatures.register("uranium_ore", Feature.ORE,
                   new OreFeatureConfig(OVERWORLD_URANIUM_ORES, 9));

   public static void registerConfiguredFeatures() {
       System.out.println("Registriert ModConfiguredFeatures für " + ClientMain.MOD_ID);
   }

}
