package net.volkov.radioisotopes.recipe;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.volkov.radioisotopes.ClientMain;

public class ModRecipes {
    public static void registerRecipes() {
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(ClientMain.MOD_ID, DeuteriumGeneratorRecipe.Serializer.ID),
                DeuteriumGeneratorRecipe.Serializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(ClientMain.MOD_ID, DeuteriumGeneratorRecipe.Type.ID),
                DeuteriumGeneratorRecipe.Type.INSTANCE);

        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(ClientMain.MOD_ID, UraniumCentrifugeRecipe.Serializer.ID),
                UraniumCentrifugeRecipe.Serializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(ClientMain.MOD_ID, UraniumCentrifugeRecipe.Type.ID),
                UraniumCentrifugeRecipe.Type.INSTANCE);
    }
}
