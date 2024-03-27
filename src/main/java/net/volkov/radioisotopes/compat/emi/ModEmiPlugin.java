package net.volkov.radioisotopes.compat.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;
import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.block.ModBlocks;
import net.volkov.radioisotopes.compat.emi.recipes.AtomicReactorEmiRecipe;
import net.volkov.radioisotopes.compat.emi.recipes.DeuteriumGeneratorEmiRecipe;
import net.volkov.radioisotopes.compat.emi.recipes.UraniumCentrifugeEmiRecipe;
import net.volkov.radioisotopes.recipe.AtomicReactorRecipe;
import net.volkov.radioisotopes.recipe.DeuteriumGeneratorRecipe;
import net.volkov.radioisotopes.recipe.UraniumCentrifugeRecipe;

public class ModEmiPlugin implements EmiPlugin {
    public static final Identifier R_SPRITE_SHEET = new Identifier(ClientMain.MOD_ID, "textures/gui/atomic_reactor_controller_gui.png");
    public static final Identifier G_SPRITE_SHEET = new Identifier(ClientMain.MOD_ID, "textures/gui/deuterium_generator_gui.png");
    public static final Identifier C_SPRITE_SHEET = new Identifier(ClientMain.MOD_ID, "textures/gui/uranium_centrifuge_gui.png");
    public static final EmiStack REACTOR_WS = EmiStack.of(ModBlocks.ATOMIC_REACTOR_CONTROLLER);
    public static final EmiStack GENERATOR_WS = EmiStack.of(ModBlocks.DEUTERIUM_GENERATOR);
    public static final EmiStack CENTRIFUGE_WS = EmiStack.of(ModBlocks.URANIUM_CENTRIFUGE);

    public static final EmiRecipeCategory REACTOR_CAT
            = new EmiRecipeCategory(new Identifier(ClientMain.MOD_ID, "atomic_reactor_controller"), REACTOR_WS, new EmiTexture(R_SPRITE_SHEET, 176, 31, 16, 16));
    public static final EmiRecipeCategory GENERATOR_CAT
            = new EmiRecipeCategory(new Identifier(ClientMain.MOD_ID, "deuterium_generator"), GENERATOR_WS, new EmiTexture(G_SPRITE_SHEET, 176, 31, 16, 16));
    public static final EmiRecipeCategory CENTRIFUGE_CAT
            = new EmiRecipeCategory(new Identifier(ClientMain.MOD_ID, "uranium_centrifuge"), CENTRIFUGE_WS, new EmiTexture(C_SPRITE_SHEET, 176, 67, 16, 16));

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(REACTOR_CAT);
        registry.addCategory(GENERATOR_CAT);
        registry.addCategory(CENTRIFUGE_CAT);
        registry.addWorkstation(REACTOR_CAT, REACTOR_WS);
        registry.addWorkstation(GENERATOR_CAT, GENERATOR_WS);
        registry.addWorkstation(CENTRIFUGE_CAT, CENTRIFUGE_WS);

        RecipeManager manager = registry.getRecipeManager();

        for (AtomicReactorRecipe recipe : manager.listAllOfType(AtomicReactorRecipe.Type.INSTANCE)) {
            registry.addRecipe(new AtomicReactorEmiRecipe(recipe));
        }
        for (DeuteriumGeneratorRecipe recipe : manager.listAllOfType(DeuteriumGeneratorRecipe.Type.INSTANCE)) {
            registry.addRecipe(new DeuteriumGeneratorEmiRecipe(recipe));
        }
        for (UraniumCentrifugeRecipe recipe : manager.listAllOfType(UraniumCentrifugeRecipe.Type.INSTANCE)) {
            registry.addRecipe(new UraniumCentrifugeEmiRecipe(recipe));
        }
    }
}
