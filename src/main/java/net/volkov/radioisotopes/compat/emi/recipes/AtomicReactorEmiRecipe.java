package net.volkov.radioisotopes.compat.emi.recipes;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Identifier;
import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.compat.emi.ModEmiPlugin;
import net.volkov.radioisotopes.item.ModItems;
import net.volkov.radioisotopes.recipe.AtomicReactorRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AtomicReactorEmiRecipe implements EmiRecipe {
    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;
    private final Identifier d_gui = new Identifier(ClientMain.MOD_ID, "textures/gui/atomic_reactor_controller_gui.png");

    public AtomicReactorEmiRecipe(AtomicReactorRecipe recipe) {
        this.id = recipe.getId();
        this.input = List.of(EmiIngredient.of(recipe.getInput().get(0)));
        this.output = List.of(EmiStack.of(recipe.getOutput()));
    }
    @Override
    public EmiRecipeCategory getCategory() {
        return ModEmiPlugin.REACTOR_CAT;
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
        return 38;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addFillingArrow(26, 1, 5000);
        widgets.addSlot(input.get(0), 0, 0);
        widgets.addSlot(EmiStack.of(ModItems.NUCLEAR_FUEL_STACK), 0, 20);
        widgets.addTexture(d_gui, 20, 22, 18, 15, 176, 0);
        widgets.addSlot(output.get(0), 58, 0).recipeContext(this);
    }
}
