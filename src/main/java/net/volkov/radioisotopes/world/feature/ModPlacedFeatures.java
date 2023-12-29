package net.volkov.radioisotopes.world.feature;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;

public class ModPlacedFeatures {
    public static final RegistryEntry<PlacedFeature> URANIUM_ORE_PLACED = PlacedFeatures.register("uranium_ore_placed",
            ModConfiguredFeatures.URANIUM_ORE, ModOreFeatures.modifiersWithCount(5,
                    HeightRangePlacementModifier.trapezoid(YOffset.fixed(-64), YOffset.fixed(-24))));
    public static final RegistryEntry<PlacedFeature> LEAD_ORE_PLACED = PlacedFeatures.register("lead_ore_placed",
            ModConfiguredFeatures.LEAD_ORE, ModOreFeatures.modifiersWithCount(7,
                    HeightRangePlacementModifier.trapezoid(YOffset.fixed(8), YOffset.fixed(85))));

    public static final RegistryEntry<PlacedFeature> LITHIUM_ORE_PLACED = PlacedFeatures.register("lithium_ore_placed",
            ModConfiguredFeatures.LITHIUM_ORE, ModOreFeatures.modifiersWithCount(6,
                    HeightRangePlacementModifier.trapezoid(YOffset.fixed(-15), YOffset.fixed(75))));

    public static final RegistryEntry<PlacedFeature> END_TENEBRIUM_ORE_PLACED = PlacedFeatures.register("end_tenebrium_ore_placed",
            ModConfiguredFeatures.END_TENEBRIUM_ORE, ModOreFeatures.modifiersWithCount(4,
                    HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
}
