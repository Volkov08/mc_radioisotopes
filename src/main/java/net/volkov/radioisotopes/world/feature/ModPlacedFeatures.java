package net.volkov.radioisotopes.world.feature;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;

public class ModPlacedFeatures {
    public static final RegistryEntry<PlacedFeature> URANIUM_ORE_PLACED = PlacedFeatures.register("uranium_ore_placed",
            ModConfiguredFeatures.URANIUM_ORE, ModOreFeatures.modifiersWithCount(4,
                    HeightRangePlacementModifier.trapezoid(YOffset.fixed(-64), YOffset.fixed(-24))));
    public static final RegistryEntry<PlacedFeature> LEAD_ORE_PLACED = PlacedFeatures.register("lead_ore_placed",
            ModConfiguredFeatures.LEAD_ORE, ModOreFeatures.modifiersWithCount(6,
                    HeightRangePlacementModifier.trapezoid(YOffset.fixed(24), YOffset.fixed(85))));
}
