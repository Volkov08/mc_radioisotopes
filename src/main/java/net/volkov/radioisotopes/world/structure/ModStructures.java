package net.volkov.radioisotopes.world.structure;

import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.mixin.StructureFeatureAccessor;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;
import net.volkov.radioisotopes.ClientMain;

public class ModStructures {
    /**
     /**
     * Registers the structure itself and sets what its path is. In this case, the
     * structure will have the Identifier of structure_tutorial:sky_structures.
     *
     * It is always a good idea to register your Structures so that other mods and datapacks can
     * use them too directly from the registries. It great for mod/datapacks compatibility.
     */
    public static StructureFeature<?> SKY_STRUCTURES = new SkyStructures();

    public static void registerStructureFeatures() {
        // The generation step for when to generate the structure. there are 10 stages you can pick from!
        // This surface structure stage places the structure before plants and ores are generated.
        StructureFeatureAccessor.callRegister(ClientMain.MOD_ID + ":sky_structures",
                SKY_STRUCTURES, GenerationStep.Feature.SURFACE_STRUCTURES);
    }
}