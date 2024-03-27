package net.volkov.radioisotopes.compat.emi.recipes;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.compat.emi.ModEmiPlugin;
import net.volkov.radioisotopes.item.ModItems;
import net.volkov.radioisotopes.recipe.DeuteriumGeneratorRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DeuteriumGeneratorEmiRecipe implements EmiRecipe {
    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;
    private final Identifier d_gui = new Identifier(ClientMain.MOD_ID, "textures/gui/deuterium_generator_gui.png");

    public DeuteriumGeneratorEmiRecipe(DeuteriumGeneratorRecipe recipe) {
        this.id = recipe.getId();
        this.input = List.of(EmiIngredient.of(recipe.getInput().get(0)));
        this.output = List.of(EmiStack.of(recipe.getOutput()));
    }
    @Override
    public EmiRecipeCategory getCategory() {
        return ModEmiPlugin.GENERATOR_CAT;
    }

    @Override
    public @Nullable Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output;
    }

    @Override
    public int getDisplayWidth() {
        return 76;
    }

    @Override
    public int getDisplayHeight() {
        return 58;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addFillingArrow(26, 1, 5000);
        widgets.addSlot(input.get(0), 0, 0);
        widgets.addSlot(EmiStack.of(Items.POTATO), 0, 20);
        widgets.addSlot(EmiStack.of(ModItems.FULL_LEAD_BATTERY), 0, 40);
        widgets.addTexture(d_gui, 19, 32, 14, 14, 58, 37);
        widgets.addAnimatedTexture(d_gui, 19, 32, 14, 14, 176, 0, 15000, false, true, true);
        widgets.addSlot(output.get(0), 58, 0).recipeContext(this);
    }
}
