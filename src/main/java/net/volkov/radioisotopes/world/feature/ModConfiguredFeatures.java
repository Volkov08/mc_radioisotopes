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

    public static final List<OreFeatureConfig.Target> OVERWORLD_LEAD_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
                    ModBlocks.LEAD_ORE.getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES,
                    ModBlocks.DEEPSLATE_LEAD_ORE.getDefaultState()));

    public static final List<OreFeatureConfig.Target> OVERWORLD_LITHIUM_ORES = List.of(OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
            ModBlocks.LITHIUM_ORE.getDefaultState()));

   public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> URANIUM_ORE =
           ConfiguredFeatures.register("uranium_ore", Feature.ORE,
                   new OreFeatureConfig(OVERWORLD_URANIUM_ORES, 9, 0.6f));

   public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> LEAD_ORE =
           ConfiguredFeatures.register("lead_ore", Feature.ORE,
                   new OreFeatureConfig(OVERWORLD_LEAD_ORES, 12));

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> LITHIUM_ORE =
            ConfiguredFeatures.register("lithium_ore", Feature.ORE,
                    new OreFeatureConfig(OVERWORLD_LITHIUM_ORES, 7));

   public static void registerConfiguredFeatures() {
       System.out.println("Registriert ModConfiguredFeatures für " + ClientMain.MOD_ID);
   }

}
