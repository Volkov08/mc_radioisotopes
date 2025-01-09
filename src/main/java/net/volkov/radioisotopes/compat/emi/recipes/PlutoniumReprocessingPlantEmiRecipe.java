package net.volkov.radioisotopes.compat.emi.recipes;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.compat.emi.ModEmiPlugin;
import net.volkov.radioisotopes.item.ModItems;
import net.volkov.radioisotopes.recipe.PlutoniumReprocessingPlantRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlutoniumReprocessingPlantEmiRecipe implements EmiRecipe {
    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;
    private final Identifier c_gui = new Identifier(ClientMain.MOD_ID, "textures/gui/plutonium_reprocessing_plant_gui.png");

    public PlutoniumReprocessingPlantEmiRecipe(PlutoniumReprocessingPlantRecipe recipe) {
        this.id = recipe.getId();
        this.input = List.of(EmiIngredient.of(recipe.getInput().get(0)), EmiIngredient.of(recipe.getInput().get(1)));
        this.output = List.of(EmiStack.of(recipe.getOutput()));
    }
    @Override
    public EmiRecipeCategory getCategory() {
        return ModEmiPlugin.PLUTONIUM_CAT;
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
        return 96;
    }

    @Override
    public int getDisplayHeight() {
        return 58;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addFillingArrow(46, 1, 5000);

        NbtCompound nbtData = new NbtCompound();
        if (output.get(0).getAmount() == 1) {
            nbtData.putInt("radioisotopes.depletion", 25000);
            widgets.addSlot(EmiStack.of(ModItems.NUCLEAR_FUEL_STACK, nbtData), 0, 0);
        } else if (output.get(0).getAmount() == 2) {
            nbtData.putInt("radioisotopes.depletion", 50000);
            widgets.addSlot(EmiStack.of(ModItems.NUCLEAR_FUEL_STACK, nbtData), 0, 0);
        } else {
            nbtData.putInt("radioisotopes.depletion", 75000);
            widgets.addSlot(EmiStack.of(ModItems.NUCLEAR_FUEL_STACK, nbtData), 0, 0);
        }

        widgets.addSlot(input.get(1), 20, 0);
        widgets.addSlot(EmiStack.of(Items.POTATO), 0, 20);
        widgets.addSlot(EmiStack.of(ModItems.FULL_LEAD_BATTERY).setRemainder(EmiStack.of(ModItems.LEAD_BATTERY)), 0, 40);
        widgets.addTexture(c_gui, 19, 32, 14, 14, 34, 37);
        widgets.addAnimatedTexture(c_gui, 19, 32, 14, 14, 176, 0, 15000, false, true, true);
        widgets.addSlot(output.get(0), 78, 0).recipeContext(this);
    }
}
